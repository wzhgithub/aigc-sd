package com.yiyun.ai.service.config;

import com.google.gson.Gson;
import com.yiyun.ai.core.api.business.sd.SDServerlessAPI;
import com.yiyun.ai.core.api.business.sd.SDServerlessConfig;
import com.yiyun.ai.core.api.business.wx.WXCloudAPI;
import com.yiyun.ai.core.api.business.wx.WXCloudConfig;
import com.yiyun.ai.core.api.db.DatabaseSQLStringTemplateLoaderConfig;
import com.yiyun.ai.service.sd.StableDiffusionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false, value = "serviceAutoConfig")
public class ServiceAutoConfig {

    @Bean
    public StableDiffusionService newStableDiffusionService(
            Gson gson,
            SDServerlessConfig sdServerlessConfig,
            DatabaseSQLStringTemplateLoaderConfig databaseSQLStringTemplateLoaderConfig,
            WXCloudConfig wxCloudConfig,
            WXCloudAPI wxCloudAPI,
            SDServerlessAPI sdServerlessAPI) {
        return new StableDiffusionService(gson,
                sdServerlessAPI,
                wxCloudAPI,
                wxCloudConfig,
                sdServerlessConfig,
                databaseSQLStringTemplateLoaderConfig);
    }

}
