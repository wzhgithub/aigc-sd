package com.yiyun.ai.core.api.websocket;

public interface WSHandler<T, R> {
    R handle(T t);
}
