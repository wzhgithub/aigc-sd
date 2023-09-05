package com.yiyun.ai.core.api.config;

import com.yiyun.ai.core.api.business.sd.SDServerlessAPI;
import com.yiyun.ai.core.api.business.sd.SDServerlessConfig;
import com.yiyun.ai.core.api.business.wx.WXCloudAPI;
import com.yiyun.ai.core.api.business.wx.WXCloudConfig;
import com.yiyun.ai.core.api.business.wx.WXLoginConfig;
import com.yiyun.ai.core.api.db.cloud.DatabaseSQLStringTemplateLoaderConfig;
import com.yiyun.ai.core.api.http.ApacheClientConfig;
import com.yiyun.ai.core.api.http.ApacheHttpClient;
import com.yiyun.ai.core.api.http.CustomGsonDecoder;
import com.yiyun.ai.core.api.http.CustomGsonEncoder;
import feign.Feign;
import feign.Logger;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@MapperScan("com.yiyun.ai.core.api.db.mysql.mapper")
@Configuration(proxyBeanMethods = false, value = "feignEngineAutoConfigure")
@EnableConfigurationProperties(value = {
        ApacheClientConfig.class,
        WXCloudConfig.class,
        WXLoginConfig.class,
        SDServerlessConfig.class,
        DatabaseSQLStringTemplateLoaderConfig.class})
public class CloudApiAutoConfigure {
    @Bean
    public Feign.Builder feignEngine(ApacheClientConfig apacheClientConfig) {
        return Feign.builder()
                .logLevel(Logger.Level.FULL)
                .decoder(new CustomGsonDecoder())
                .encoder(new CustomGsonEncoder())
                .client(new ApacheHttpClient(apacheClientConfig));
    }

    @Bean
    public WXCloudAPI newWXCloudAPI(Feign.Builder feignEngine,
                                    WXCloudConfig wxCloudConfig) {
        return wxCloudConfig.newInstance(feignEngine);
    }

    @Bean
    public SDServerlessAPI newSDServerlessAPI(Feign.Builder feignEngine,
                                              SDServerlessConfig sdServerlessConfig) {
        return sdServerlessConfig.getHttp().newInstance(feignEngine);
    }
}
