package com.yiyun.ai.core.api.business.sd;

import feign.Headers;
import feign.RequestLine;

public interface SDServerlessAPI {

    @RequestLine("POST /sdapi/v1/img2img")
    @Headers("Content-Type: application/json")
    SDImage2ImageStruct.SDImage2ImageResponse image2image(SDImage2ImageStruct.SDImage2ImageRequest request);
}
