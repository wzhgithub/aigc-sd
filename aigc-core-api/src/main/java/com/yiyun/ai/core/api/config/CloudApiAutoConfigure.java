package com.yiyun.ai.core.api.config;

import com.yiyun.ai.core.api.business.sd.SDServerlessAPI;
import com.yiyun.ai.core.api.business.sd.SDServerlessConfig;
import com.yiyun.ai.core.api.business.wx.WXCloudAPI;
import com.yiyun.ai.core.api.business.wx.WXCloudConfig;
import com.yiyun.ai.core.api.db.DatabaseSQLStringTemplateLoaderConfig;
import com.yiyun.ai.core.api.http.ApacheClientConfig;
import com.yiyun.ai.core.api.http.ApacheHttpClient;
import com.yiyun.ai.core.api.http.CustomGsonDecoder;
import feign.Feign;
import feign.gson.GsonEncoder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false, value = "feignEngineAutoConfigure")
@EnableConfigurationProperties(value = {
        ApacheClientConfig.class,
        WXCloudConfig.class,
        SDServerlessConfig.class,
        DatabaseSQLStringTemplateLoaderConfig.class})
public class CloudApiAutoConfigure {

    @Bean
    public ApacheHttpClient apacheHttpClient(ApacheClientConfig apacheClientConfig) {
        return new ApacheHttpClient(apacheClientConfig);
    }

    @Bean
    public Feign.Builder feignEngine(ApacheHttpClient apacheHttpClient) {
        return Feign.builder()
                .decoder(new CustomGsonDecoder())
                .encoder(new GsonEncoder())
                .client(apacheHttpClient);
    }

    @Bean
    public WXCloudAPI newWXCloudAPI(Feign.Builder feignEngine,
                                    WXCloudConfig wxCloudConfig) {
        return wxCloudConfig.newInstance(feignEngine);
    }

    @Bean
    public SDServerlessAPI newSDServerlessAPI(Feign.Builder feignEngine,
                                              SDServerlessConfig sdServerlessConfig) {
        return sdServerlessConfig.newInstance(feignEngine);
    }
}
