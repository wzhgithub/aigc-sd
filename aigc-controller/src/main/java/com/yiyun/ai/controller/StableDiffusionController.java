package com.yiyun.ai.controller;

import com.yiyun.ai.service.request.wx.WXQCRGenRequest;
import com.yiyun.ai.service.sd.StableDiffusionService;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.yinyun.ai.common.constance.ServiceConstance.RequestId;

@RestController
@RequestMapping("/stable-diffusion")
public class StableDiffusionController {
    @Autowired
    StableDiffusionService stableDiffusionService;

    @RequestMapping("/qrcode")
    public ExceptionController.CommonResponse generate(@RequestHeader("X-Request-Id") String requestId,
                                                       @RequestBody WXQCRGenRequest request) throws Exception {
        MDC.put(RequestId.name(), requestId);
        stableDiffusionService.generateQRCode(request);
        return new ExceptionController.CommonResponse(200, "ok");
    }
}
