package com.yiyun.ai.core.api.business.wx;

import com.yiyun.ai.core.api.http.AbstractFeignHttpConfig;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "wx.cloud.config")
public class WXCloudConfig extends AbstractFeignHttpConfig<WXCloudAPI> {
    String accessToken;
}
