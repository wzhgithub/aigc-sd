package com.yiyun.ai.controller;

import com.yiyun.ai.core.api.business.wx.LoginResponse;
import com.yiyun.ai.service.wx.MiniProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/aigc/app")
public class AppController {
    @Autowired
    MiniProgramService miniProgramService;

    // 微信小程序登录
    @RequestMapping(value = "/login", method = GET)
    public LoginResponse login(@RequestParam("js_code") String jsCode) throws Exception {
        return miniProgramService.login(jsCode);
    }

    @RequestMapping(value = "/live", method = GET)
    public ExceptionController.CommonResponse live() throws Exception {
        return new ExceptionController.CommonResponse(200, "success");
    }
}
