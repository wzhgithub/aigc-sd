package com.yiyun.ai.core.api.config;

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
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;

class CloudApiAutoConfigureJsonTest {

    public static void main(String[] args) throws Exception {
        String base64Encode = ImageUtil.imageQRCBase64Encode("aigc-core-api/src/test/resources/img_wzh.png");
//        String base64Encode = ImageUtil.imageBase64Encode("aigc-core-api/src/test/resources/test.png");
//        try (FileWriter fileWriter = new FileWriter("img64.txt")) {
//            fileWriter.write(base64Encode);
//            fileWriter.flush();
//        }
        SDServerlessConfig.SDServerHttpConfig http = new SDServerlessConfig.SDServerHttpConfig();
        http.setHost("https://service-9oonca4r-1304634141.bj.apigw.tencentcs.com/");
        http.setClazz(SDServerlessAPI.class);
        SDServerlessAPI target = Feign.builder()
                .logLevel(Logger.Level.FULL)
                .decoder(new CustomGsonDecoder())
                .encoder(new GsonEncoder())
                .client(new ApacheHttpClient(new ApacheClientConfig()))
                .target(http.getClazz(), http.getHost());

        SDServerlessConfig.SDText2ImageConfig sdText2ImageConfig = new SDServerlessConfig.SDText2ImageConfig();
        sdText2ImageConfig.setRequestTemplate("/sd/");
        sdText2ImageConfig.setTemplateName("txt2img.ftlh");
        sdText2ImageConfig.afterPropertiesSet();
        String s = sdText2ImageConfig.newTxt2ImageJsonString(base64Encode);
        long st = System.currentTimeMillis();
        SDAny2ImageStruct.SDTxt2ImageResponse sdTxt2ImageResponse = target.text2image(s);
        System.out.println("time:" + (System.currentTimeMillis() - st) / 1000.0);
        for (int i = 0; i < sdTxt2ImageResponse.getImages().size(); i++) {
            String path = String.format("image-%d.png", i);
            File imageFile = new File(path);
            ImageUtil.imageBase64Decode(imageFile, sdTxt2ImageResponse.getImages().get(i));
            byte[] bytes = FileUtils.readFileToByteArray(imageFile);
            try (FileOutputStream fis = new FileOutputStream(path)) {
                fis.write(bytes);
                fis.flush();
            }
        }
    }
}