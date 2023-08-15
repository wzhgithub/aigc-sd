package com.yiyun.ai.core.api.ws;

public interface WSHandler<T, R> {
    R handle(T t);
}
