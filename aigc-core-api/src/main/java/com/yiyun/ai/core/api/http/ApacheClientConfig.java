package com.yiyun.ai.core.api.http;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Data
@ConfigurationProperties(prefix = "http.feign.apache")
public class ApacheClientConfig {
    int maxConnections = 128;
    int maxConnPerRoute = 32;
    Duration requestTimeout = Duration.ofSeconds(300);

}
