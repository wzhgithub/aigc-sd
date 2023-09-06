package com.yiyun.ai.service.sd;

import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.gson.Gson;
import com.yiyun.ai.core.api.business.sd.SDServerlessAPI;
import com.yiyun.ai.core.api.business.sd.SDServerlessConfig;
import com.yiyun.ai.core.api.business.wx.CloudDatabase;
import com.yiyun.ai.core.api.business.wx.WXCloudAPI;
import com.yiyun.ai.core.api.business.wx.WXCloudConfig;
import com.yiyun.ai.core.api.db.cloud.DataBaseOption;
import com.yiyun.ai.core.api.db.cloud.DatabaseSQLStringTemplateLoaderConfig;
import com.yiyun.ai.service.entity.SDQRCTemplate;
import com.yiyun.ai.service.request.wx.WXQCRGenRequest;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;


@Slf4j
public class StableDiffusionService implements RejectedExecutionHandler {
    private final SDServerlessAPI sdServerlessAPI;
    private final WXCloudAPI wxCloudAPI;
    private final ExecutorService pool;
    private final WXCloudConfig wxCloudConfig;
    private final DatabaseSQLStringTemplateLoaderConfig databaseSQLStringTemplateLoaderConfig;
    private final Gson gson;
    private final SDServerlessConfig sdServerlessConfig;

    private final Map<String, byte[]> imageCache;

    private final List<SDQRCTemplate> templates;

    //todo read from current project
    public byte[] readImage(String id) throws Exception {
        return imageCache.get(id);
    }

    //todo read supported template
    public List<SDQRCTemplate> templates() {
        return templates;
    }

    public StableDiffusionService(
            Gson gson,
            SDServerlessAPI sdServerlessAPI,
            WXCloudAPI wxCloudAPI,
            WXCloudConfig wxCloudConfig,
            SDServerlessConfig sdServerlessConfig,
            DatabaseSQLStringTemplateLoaderConfig databaseSQLStringTemplateLoaderConfig) {
        this.gson = gson;
        this.sdServerlessAPI = sdServerlessAPI;
        this.wxCloudAPI = wxCloudAPI;
        this.wxCloudConfig = wxCloudConfig;
        this.sdServerlessConfig = sdServerlessConfig;
        this.databaseSQLStringTemplateLoaderConfig = databaseSQLStringTemplateLoaderConfig;
        ThreadFactory build = new ThreadFactoryBuilder()
                .setUncaughtExceptionHandler((t, e) -> log.error("t:{} execute error", t, e))
                .setNameFormat("sd-pool-%d")
                .build();
        this.pool = new ThreadPoolExecutor(8, 128, 1, TimeUnit.MINUTES,
                new LinkedBlockingQueue<>(512), build, this);

        log.info("sd txt2img config {}", sdServerlessConfig.getTxt2img());
        //todo init templates and image
        this.templates = new ArrayList<>();
        this.imageCache = ImmutableMap.of();
    }

    public void generateQRCode(final WXQCRGenRequest wxqcrGenRequest) throws Exception {
        List<String> data = getQCRTaskData(wxqcrGenRequest);
        if (CollectionUtils.isEmpty(data)) {
            CloudDatabase.QueryOrUpdateDatabaseReq request = wxqcrGenRequest.newUpdateQuery(
                    SDTask.TaskStatus.FAILED,
                    databaseSQLStringTemplateLoaderConfig,
                    wxCloudConfig.getEnv());
            CloudDatabase.UpdateDatabaseResponse response = wxCloudAPI.updateDatabase(request);
            log.debug("failed update resp:{}", response);
            return;
        }
        data.stream()
                .map(e -> gson.fromJson(e, SDText2ImageTask.class))
                .peek(e -> {
                    e.setRequest(wxqcrGenRequest);
                    e.setCopyOfContextMap(MDC.getCopyOfContextMap());
                    e.setWxCloudAPI(wxCloudAPI);
                    e.setSdServerlessAPI(sdServerlessAPI);
                    e.setWxCloudConfig(wxCloudConfig);
                    e.setSdServerlessConfig(sdServerlessConfig);
                    e.setDatabaseSQLStringTemplateLoaderConfig(databaseSQLStringTemplateLoaderConfig);
                }).forEach(pool::submit);
    }

    private List<String> getQCRTaskData(WXQCRGenRequest wxqcrGenRequest) throws TemplateException, IOException {
        CloudDatabase.QueryOrUpdateDatabaseReq queryOrUpdateDatabaseReq =
                databaseSQLStringTemplateLoaderConfig.newCloudDatabaseReq(DataBaseOption.Query,
                        wxCloudConfig.getEnv(), wxqcrGenRequest.dbArgs());
        CloudDatabase.QueryDatabaseResponse queryDatabaseResponse =
                wxCloudAPI.queryDatabase(queryOrUpdateDatabaseReq);

        if (queryDatabaseResponse.getErrcode() != 0) {
            log.error("query database error:{}", queryDatabaseResponse.getErrmsg());
            return null;
        }
        List<String> data = queryDatabaseResponse.getData();
        if (CollectionUtils.isEmpty(data)) {
            log.error("query database data empty q:{}", queryDatabaseResponse);
            return null;
        }
        return data;
    }

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        // callback when task is rejected
        log.warn("sd-pool is full, reject task:{}", r);
        ((SDTask) r).updateStatus(SDTask.TaskStatus.FAILED);
    }
}
