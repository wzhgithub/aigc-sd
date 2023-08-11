package com.yiyun.ai.service.sd;

import com.yiyun.ai.core.api.business.sd.SDServerlessAPI;
import com.yiyun.ai.core.api.business.wx.WXCloudAPI;

public class StableDiffusionService {
    private final SDServerlessAPI sdServerlessAPI;
    private final WXCloudAPI wxCloudAPI;

    public StableDiffusionService(SDServerlessAPI sdServerlessAPI, WXCloudAPI wxCloudAPI) {
        this.sdServerlessAPI = sdServerlessAPI;
        this.wxCloudAPI = wxCloudAPI;
    }
}
