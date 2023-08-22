package com.yiyun.ai.core.api.config;

import com.google.common.collect.ImmutableList;
import com.yinyun.ai.common.utils.ImageUtil;
import com.yiyun.ai.core.api.business.sd.SDAny2ImageStruct;
import com.yiyun.ai.core.api.business.sd.SDServerlessAPI;
import com.yiyun.ai.core.api.business.sd.SDServerlessConfig;
import com.yiyun.ai.core.api.http.ApacheClientConfig;
import com.yiyun.ai.core.api.http.ApacheHttpClient;
import com.yiyun.ai.core.api.http.CustomGsonDecoder;
import feign.Feign;
import feign.Logger;
import feign.gson.GsonEncoder;

class CloudApiAutoConfigureTest {

    public static void main(String[] args) throws Exception {
        String base64Encode = ImageUtil.imageBase64Encode("aigc-core-api/src/test/resources/img.png");
        SDServerlessConfig.SDServerHttpConfig http = new SDServerlessConfig.SDServerHttpConfig();
        http.setHost("https://service-b2r1wow5-1304634141.bj.apigw.tencentcs.com/");
        http.setClazz(SDServerlessAPI.class);
        SDServerlessAPI target = Feign.builder()
                .logLevel(Logger.Level.FULL)
                .decoder(new CustomGsonDecoder())
                .encoder(new GsonEncoder())
                .client(new ApacheHttpClient(new ApacheClientConfig()))
                .target(http.getClazz(), http.getHost());
        SDAny2ImageStruct.SDTxt2ImageRequest sdTxt2ImageRequest
                = new SDAny2ImageStruct.SDTxt2ImageRequest();
        SDAny2ImageStruct.SDTxt2ImageRequest.AlwaysonScriptsDTO alwaysonScripts =
                new SDAny2ImageStruct.SDTxt2ImageRequest.AlwaysonScriptsDTO();
        SDAny2ImageStruct.SDTxt2ImageRequest.AlwaysonScriptsDTO.ControlnetDTO controlnet =
                new SDAny2ImageStruct.SDTxt2ImageRequest.AlwaysonScriptsDTO.ControlnetDTO();
        SDAny2ImageStruct.SDTxt2ImageRequest.AlwaysonScriptsDTO.ControlnetDTO.ArgsDTO element =
                new SDAny2ImageStruct.SDTxt2ImageRequest.AlwaysonScriptsDTO.ControlnetDTO.ArgsDTO();
        element.setModel("control_v1p_sd15_qrcode [9c780d03]");
        element.setInputImage(base64Encode);
        controlnet.setArgs(ImmutableList.of(element));
        alwaysonScripts.setControlnet(controlnet);
        sdTxt2ImageRequest.setPrompt("a cinematic shot of an impressive ants war, ant melee, armageddon");
        sdTxt2ImageRequest.setSamplerName("Euler");
        sdTxt2ImageRequest.setAlwaysonScripts(alwaysonScripts);
        SDAny2ImageStruct.SDTxt2ImageResponse sdTxt2ImageResponse =
                target.text2image(sdTxt2ImageRequest);
        System.out.println(sdTxt2ImageResponse);
    }
}