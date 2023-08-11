package com.yiyun.ai.core.api.business.sd;

import com.yiyun.ai.core.api.http.AbstractFeignHttpConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("sd.config")
public class SDServerlessConfig extends AbstractFeignHttpConfig<SDServerlessAPI> {
}
