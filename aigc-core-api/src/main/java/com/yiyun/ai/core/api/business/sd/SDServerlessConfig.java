package com.yiyun.ai.core.api.business.sd;

import com.yiyun.ai.core.api.http.AbstractFeignHttpConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Data
@ConfigurationProperties("sd.config")
public class SDServerlessConfig {

    @NestedConfigurationProperty
    SDText2ImageConfig txt2img;
    @NestedConfigurationProperty
    SDServerHttpConfig http;
    @NestedConfigurationProperty
    SDServerWSConfig ws;


    @Data
    public static class SDText2ImageConfig {
        String prompt;
        String sample;
        String model;
    }

    @Data
    public static class SDServerWSConfig {
        String url;

        public SDWebSocketClient newClient(List<AbstractSDWSJsonHandler> handlers)
                throws URISyntaxException {
            SDWebSocketClient sdWebSocketClient = new SDWebSocketClient(new URI(url), handlers);
            sdWebSocketClient.connect();
            return sdWebSocketClient;
        }
    }

    public static class SDServerHttpConfig extends AbstractFeignHttpConfig<SDServerlessAPI> {
    }
}
