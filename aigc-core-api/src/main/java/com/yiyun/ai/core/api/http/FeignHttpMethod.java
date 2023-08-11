package com.yiyun.ai.core.api.http;

public enum FeignHttpMethod {
    GET, POST, PUT, DELETE;

    @Override
    public String toString() {
        return this.name().toUpperCase() + " ";
    }
}
