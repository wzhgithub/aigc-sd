package com.yiyun.ai.service.wx;

import com.yiyun.ai.core.api.business.wx.LoginResponse;
import com.yiyun.ai.core.api.business.wx.WXCloudAPI;
import com.yiyun.ai.core.api.business.wx.WXLoginConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MiniProgramService {
    private final WXCloudAPI api;
    private final WXLoginConfig loginConfig;

    public MiniProgramService(WXCloudAPI api, WXLoginConfig loginConfig) {
        this.api = api;
        this.loginConfig = loginConfig;
        log.debug("login config: {}", this.loginConfig);
    }

    public LoginResponse login(String code) {
        /* https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/login.html */
        LoginResponse login = api.login(loginConfig.getAppId(), loginConfig.getSecret(), code);
        //todo 处理登录结果 login
        login.setSessionKey("");
        return login;
    }
}
