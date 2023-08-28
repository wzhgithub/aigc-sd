package com.yiyun.ai.controller;

import com.yiyun.ai.service.entity.SDQRCTemplate;
import com.yiyun.ai.service.request.wx.WXQCRGenRequest;
import com.yiyun.ai.service.sd.StableDiffusionService;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yinyun.ai.common.constance.ServiceConstance.RequestId;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

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

    @RequestMapping(value = "/template/list", method = GET)
    public ExceptionController.CommonResponse templateList() throws Exception {
        List<SDQRCTemplate> templates = stableDiffusionService.templates();
        return new ExceptionController.CommonResponse(200, "ok", templates);
    }

    @ResponseBody
    @RequestMapping(
            value = "/template/{id}",
            method = GET,
            produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImage(@PathVariable("id") String id) throws Exception {
        return stableDiffusionService.readImage(id);
    }

}
