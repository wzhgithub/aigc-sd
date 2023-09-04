package com.yiyun.ai.core.api.business.wx;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "wx.login")
public class WXLoginConfig {
    String appId;
    String secret;
}
