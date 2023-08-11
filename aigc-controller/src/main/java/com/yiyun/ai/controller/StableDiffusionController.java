package com.yiyun.ai.controller;

import com.yiyun.ai.service.sd.StableDiffusionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stable-diffusion")
public class StableDiffusionController {
    @Autowired
    StableDiffusionService stableDiffusionService;

    @RequestMapping("/generate")
    public String generate() {
        return "";
    }
}
