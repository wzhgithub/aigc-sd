package com.yiyun.ai.core.api.business.sd;

import feign.Body;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface SDServerlessAPI {

    @RequestLine("POST /release/sdapi/v1/img2img")
    @Headers("Content-Type: application/json")
    SDAny2ImageStruct.SDImage2ImageResponse image2image(SDAny2ImageStruct.SDImage2ImageRequest request);

    @RequestLine("POST /release/sdapi/v1/txt2img")
    @Headers("Content-Type: application/json")
    SDAny2ImageStruct.SDTxt2ImageResponse text2image(SDAny2ImageStruct.SDTxt2ImageRequest sdTxt2ImageRequest);

    @Body("{json}")
    @Headers("Content-Type: application/json")
    @RequestLine("POST /release/sdapi/v1/txt2img")
    SDAny2ImageStruct.SDTxt2ImageResponse text2image(@Param("json") String requestJSON);
}
