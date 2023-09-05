package com.yiyun.ai.core.api.business.wx;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ToString
@ConfigurationProperties(prefix = "wx.login")
public class WXLoginConfig {
    String appId;
    String secret;
}
