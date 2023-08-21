package com.yiyun.ai.core.api.business.wx;

import com.yiyun.ai.core.api.http.AbstractFeignHttpConfig;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@Setter
@Getter
@ConfigurationProperties(prefix = "wx.cloud.config")
public class WXCloudConfig extends AbstractFeignHttpConfig<WXCloudAPI> {
    String accessToken;
    String env;
    @NestedConfigurationProperty
    FileInfo file;

    public String genPath(String user, String fileName) {
        return String.format(file.prefix, user, fileName);
    }

    @Data
    public static class FileInfo {
        String prefix;
    }
}
