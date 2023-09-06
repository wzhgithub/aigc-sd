package com.yiyun.ai.core.api.business.wx;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.Response;

import java.io.File;
import java.net.URI;

/**
 * <a href="https://developers.weixin.qq.com/miniprogram/dev/wxcloud/reference-http-api/">...</a>
 */
public interface WXCloudAPI {

    @RequestLine("POST /tcb/databaseupdate")
    @Headers("Content-Type: application/json")
    CloudDatabase.UpdateDatabaseResponse updateDatabase(CloudDatabase.QueryOrUpdateDatabaseReq request);

    @RequestLine("POST /tcb/databasequery")
    @Headers("Content-Type: application/json")
    CloudDatabase.QueryDatabaseResponse queryDatabase(CloudDatabase.QueryOrUpdateDatabaseReq request);

    @RequestLine("POST /tcb/batchdownloadfile")
    @Headers("Content-Type: application/json")
    CloudFile.CloudFileDownloadResponse batchDownloadFile(
            CloudFile.CloudFileDownloadReq request
    );


    @RequestLine("POST /tcb/uploadfile")
    @Headers("Content-Type: application/json")
    CloudFile.CloudUploadFileResponse uploadFileRequest(CloudFile.CloudUploadFileReq request);


    /**
     * <a href="https://developers.weixin.qq.com/miniprogram/dev/wxcloudrun/src/development/weixin/callback.html#%E4%BA%8C%E3%80%81%E4%B8%BB%E5%8A%A8%E5%9B%9E%E5%A4%8D">...</a>
     *
     * @param request
     * @return
     */
    @RequestLine("POST /cgi-bin/message/custom/send")
    CloudMessage.MessageResponse sendMessage(CloudMessage.MiniProgramRequestMessage request);

    @RequestLine("POST")
    @Headers("Content-Type: multipart/form-data")
    Response uploadFileToCos(URI basicUrl,
                             @Param("file") File file,
                             @Param("key") String key,
                             @Param("Signature") String sign,
                             @Param("x-cos-security-token") String token,
                             @Param("x-cos-meta-fileid") String fileId);

    /**
     * <a href="https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/user-login/code2Session.html">...</a>
     */
    @RequestLine("GET sns/jscode2session?appid={app_id}&secret={secret}&js_code={js_code}&grant_type=authorization_code")
    LoginResponse login(
            @Param("app_id") String appId,
            @Param("secret") String secret,
            @Param("js_code") String jsCode
    );

}
