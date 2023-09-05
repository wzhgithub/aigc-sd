package com.yiyun.ai.service.sd;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.annotations.SerializedName;
import com.yinyun.ai.common.utils.ImageUtil;
import com.yiyun.ai.core.api.business.sd.SDAny2ImageStruct;
import com.yiyun.ai.core.api.business.sd.SDServerlessAPI;
import com.yiyun.ai.core.api.business.sd.SDServerlessConfig;
import com.yiyun.ai.core.api.business.wx.*;
import com.yiyun.ai.core.api.db.cloud.DataBaseOption;
import com.yiyun.ai.core.api.db.cloud.DatabaseSQLStringTemplateLoaderConfig;
import com.yiyun.ai.service.request.wx.AbstractWXAIGCRequest;
import com.yiyun.ai.service.request.wx.WXQCRGenRequest;
import feign.Response;
import freemarker.template.TemplateException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
@Getter
@NoArgsConstructor
@ToString(callSuper = true)
public class SDText2ImageTask extends AbstractWXAIGCRequest implements SDTask {

    @Setter
    @SerializedName("raw_file_id")
    String rawFileId;

    @Setter
    @SerializedName("gen_file_id")
    String genFileId = "";

    @Setter
    @SerializedName("template_id")
    String templateId;

    @Setter
    transient private SDServerlessAPI sdServerlessAPI;
    @Setter
    transient private WXCloudAPI wxCloudAPI;
    @Setter
    transient private WXQCRGenRequest request;
    @Setter
    transient private Map<String, String> copyOfContextMap;
    @Setter
    transient private WXCloudConfig wxCloudConfig;
    @Setter
    transient private SDServerlessConfig sdServerlessConfig;
    @Setter
    transient private DatabaseSQLStringTemplateLoaderConfig databaseSQLStringTemplateLoaderConfig;

    @Override
    public void updateStatus(TaskStatus status) {
        MDC.setContextMap(copyOfContextMap);
        try {
            wxCloudAPI.updateDatabase(newUpdateQuery(status), wxCloudConfig.getAccessToken());
        } catch (TemplateException | IOException e) {
            log.error("update status error", e);
        }
    }

    /**
     * 核心执行链路，涉及多次调用 api 接口
     */
    @Override
    public void run() {
        MDC.setContextMap(copyOfContextMap);
        // 调用云开发API生成图片
        CloudMessage.MiniProgramRequestMessage requestMessage = null;
        try {
            log.info("start generate text2image task");
            B64Result result = getB64Result();
            if (StringUtils.isBlank(result.b64EncodeStr)) {
                log.error("b64EncodeStr is null cloudFileDownloadResponse:{}", result.cloudFileDownloadResponse);
                throw new IllegalArgumentException("base64EncodeStr is null");
            }
            SDAny2ImageStruct.SDTxt2ImageResponse sdTxt2ImageResponse = getSdTxt2ImageResponse(result);
            // 保存图片到云文件
            genFileId = uploadQRCode2Cos(sdTxt2ImageResponse);
            //update task status
            CloudDatabase.UpdateDatabaseResponse updateDatabaseResponse =
                    wxCloudAPI.updateDatabase(newUpdateQuery(TaskStatus.SUCCESS), wxCloudConfig.getAccessToken());
            if (updateDatabaseResponse.getErrcode() != 0) {
                throw new RuntimeException(updateDatabaseResponse.getErrmsg());
            }

            requestMessage = new CloudMessage.MiniProgramRequestMessage(user.getOpenid(), "二维码生成成功");
        } catch (Exception e) {
            log.error("sdServerlessAPI.text2image error", e);
            try {
                wxCloudAPI.updateDatabase(newUpdateQuery(TaskStatus.FAILED), wxCloudConfig.getAccessToken());
            } catch (TemplateException | IOException ex) {
                log.error("sdServerlessAPI.updateDatabase error", ex);
            }
            requestMessage = new CloudMessage.MiniProgramRequestMessage(user.getOpenid(), "二维码生成异常,请重试");
        }
        CloudMessage.MessageResponse messageResponse = wxCloudAPI.sendMessage(requestMessage);
        log.info("end generate text2image task message response:{}", messageResponse);
    }

    private CloudDatabase.QueryOrUpdateDatabaseReq newUpdateQuery(TaskStatus status) throws TemplateException, IOException {
        String format = String.format("{%s:%s}", "task_id", taskId);
        String formatUpdate = String.format("{%s:%s, %s:%s}", "gen_file_id", genFileId, "status", status.name());
        return databaseSQLStringTemplateLoaderConfig.newCloudDatabaseReq(DataBaseOption.Update,
                wxCloudConfig.getEnv(),
                ImmutableMap.of("collection", collection,
                        "condition", format,
                        "update", formatUpdate)
        );
    }

    private String uploadQRCode2Cos(SDAny2ImageStruct.SDTxt2ImageResponse sdTxt2ImageResponse) throws Exception {
        String s = sdTxt2ImageResponse.getImages().get(0);
        File tempFile = File.createTempFile("temp-gen-qrc", ".png");
        ImageUtil.imageBase64Decode(tempFile, s);
        String path = getPath(tempFile);
        CloudFile.CloudUploadFileResponse cloudUploadFileResponse =
                wxCloudAPI.uploadFileRequest(newCloudUploadFileRequest(path), wxCloudConfig.getAccessToken());
        if (cloudUploadFileResponse.getErrcode() != 0) {
            throw new RuntimeException(cloudUploadFileResponse.getErrmsg());
        }
        try (Response response = wxCloudAPI.uploadFileToCos(cloudUploadFileResponse.newUri(),
                tempFile,
                path,
                cloudUploadFileResponse.getAuthorization(),
                cloudUploadFileResponse.getToken(),
                cloudUploadFileResponse.getCosFileId()
        )) {
            if (response.status() != 200) {
                log.debug("cos resp:{}", IOUtils.toString(response.body().asInputStream(), StandardCharsets.UTF_8));
                throw new RuntimeException("uploadFileToCos error");
            }
        }
        return cloudUploadFileResponse.getFileId();
    }

    private SDAny2ImageStruct.SDTxt2ImageResponse getSdTxt2ImageResponse(B64Result result) {
        String jsonString = sdServerlessConfig.getTxt2img().newTxt2ImageJsonString(result.b64EncodeStr, templateId);
        SDAny2ImageStruct.SDTxt2ImageResponse sdTxt2ImageResponse = sdServerlessAPI.text2image(jsonString);
        if (CollectionUtils.isEmpty(sdTxt2ImageResponse.getImages())) {
            throw new RuntimeException("sdServerlessAPI.text2image response.images is empty");
        }
        return sdTxt2ImageResponse;
    }

    private B64Result getB64Result() {
        CloudFile.CloudFileDownloadResponse cloudFileDownloadResponse =
                wxCloudAPI.batchDownloadFile(newCloudFileDownloadReq(), wxCloudConfig.getAccessToken());
        String b64EncodeStr = cloudFileDownloadResponse.getFileList().get(0).getB64EncodeStr();
        return new B64Result(cloudFileDownloadResponse, b64EncodeStr);
    }

    private static class B64Result {
        public final CloudFile.CloudFileDownloadResponse cloudFileDownloadResponse;
        public final String b64EncodeStr;

        public B64Result(CloudFile.CloudFileDownloadResponse cloudFileDownloadResponse, String b64EncodeStr) {
            this.cloudFileDownloadResponse = cloudFileDownloadResponse;
            this.b64EncodeStr = b64EncodeStr;
        }
    }

    public CloudFile.CloudUploadFileReq newCloudUploadFileRequest(String path) {
        CloudFile.CloudUploadFileReq cloudUploadFileReq = new CloudFile.CloudUploadFileReq();
        cloudUploadFileReq.setEnv(wxCloudConfig.getEnv());
        cloudUploadFileReq.setPath(path);
        return cloudUploadFileReq;
    }

    private String getPath(File file) {
        return wxCloudConfig.genPath(user.getOpenid(), file.getName());
    }

    private SDAny2ImageStruct.SDTxt2ImageRequest getSdTxt2ImageRequest(String b64EncodeStr) {
        SDServerlessConfig.SDText2ImageConfig txt2img = sdServerlessConfig.getTxt2img();
        SDAny2ImageStruct.SDTxt2ImageRequest sdTxt2ImageRequest = new SDAny2ImageStruct.SDTxt2ImageRequest();
        sdTxt2ImageRequest.setPrompt(txt2img.getPrompt());
        sdTxt2ImageRequest.setSamplerName(txt2img.getSample());
        SDAny2ImageStruct.SDTxt2ImageRequest.AlwaysonScriptsDTO alwaysonScripts =
                new SDAny2ImageStruct.SDTxt2ImageRequest.AlwaysonScriptsDTO();
        SDAny2ImageStruct.SDTxt2ImageRequest.AlwaysonScriptsDTO.ControlnetDTO controlnet =
                new SDAny2ImageStruct.SDTxt2ImageRequest.AlwaysonScriptsDTO.ControlnetDTO();
        SDAny2ImageStruct.SDTxt2ImageRequest.AlwaysonScriptsDTO.ControlnetDTO.ArgsDTO element =
                new SDAny2ImageStruct.SDTxt2ImageRequest.AlwaysonScriptsDTO.ControlnetDTO.ArgsDTO();
        element.setInputImage(b64EncodeStr);
        element.setModel(txt2img.getModel());
        controlnet.setArgs(ImmutableList.of(element));
        alwaysonScripts.setControlnet(controlnet);
        sdTxt2ImageRequest.setAlwaysonScripts(alwaysonScripts);
        return sdTxt2ImageRequest;
    }

    private CloudFile.CloudFileDownloadReq newCloudFileDownloadReq() {
        CloudFile.CloudFileDownloadReq cloudFileDownloadReq = new CloudFile.CloudFileDownloadReq();
        cloudFileDownloadReq.setEnv(wxCloudConfig.getEnv());
        CloudFile.CloudFileDownloadReq.FileListDTO fileListDTO = new CloudFile.CloudFileDownloadReq.FileListDTO();
        fileListDTO.setFileid(rawFileId);
        fileListDTO.setMaxAge(3600L);
        cloudFileDownloadReq.setFileList(ImmutableList.of(fileListDTO));
        return cloudFileDownloadReq;
    }

}
