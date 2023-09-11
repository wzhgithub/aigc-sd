package com.yiyun.ai.core.api.http;

import com.google.common.io.CharStreams;
import feign.Response;
import feign.gson.GsonDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.lang.reflect.Type;

import static feign.Util.UTF_8;

@Slf4j
public class CustomGsonDecoder extends GsonDecoder {
    @Override
    public Object decode(Response response, Type type) throws IOException {
        log.debug("return type:{}", type);
        String typeName = type.getTypeName();

        if (StringUtils.equals(String.class.getTypeName(), typeName)) {
            return CharStreams.toString(response.body().asReader(UTF_8));
        }

        if (StringUtils.equals(Response.class.getTypeName(), typeName)) {
            return response;
        }
        return super.decode(response, type);
    }
}
