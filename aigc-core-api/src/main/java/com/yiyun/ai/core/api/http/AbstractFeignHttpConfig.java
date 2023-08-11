package com.yiyun.ai.core.api.http;

import feign.Feign;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractFeignHttpConfig<T> {
    protected String host;
    protected Class<T> clazz;

    public T newInstance(Feign.Builder builder) {
        return builder.target(clazz, host);
    }
}
