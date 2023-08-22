package com.yiyun.ai.core.api.http;

import feign.RequestTemplate;
import feign.gson.GsonEncoder;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;

@Slf4j
public class CustomGsonEncoder extends GsonEncoder {
    @Override
    public void encode(Object object, Type bodyType, RequestTemplate template) {
        if (object instanceof String) {
            template.body((String) object);
            return;
        }
        super.encode(object, bodyType, template);
    }
}
