package com.yiyun.ai.service.config;

import com.yiyun.ai.core.api.business.sd.SDServerlessAPI;
import com.yiyun.ai.core.api.business.wx.WXCloudAPI;
import com.yiyun.ai.service.sd.StableDiffusionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false, value = "serviceAutoConfig")
public class ServiceAutoConfig {

    @Bean
    public StableDiffusionService newStableDiffusionService(
            WXCloudAPI wxCloudAPI, SDServerlessAPI sdServerlessAPI) {
        return new StableDiffusionService(sdServerlessAPI, wxCloudAPI);
    }

}
