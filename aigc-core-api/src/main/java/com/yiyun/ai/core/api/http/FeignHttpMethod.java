package com.yiyun.ai.core.api.http;

public enum FeignHttpMethod {
    GET, POST, PUT, DELETE;

    @Override
    public String toString() {
        return this.name().toUpperCase() + " ";
    }

    public static class ContentTypes {
        public static final String CONTENT_TYPE_APPLICATION_JSON = "Content-Type: application/json";
    }
}
