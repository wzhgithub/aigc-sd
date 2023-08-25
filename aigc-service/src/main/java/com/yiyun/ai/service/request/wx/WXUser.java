package com.yiyun.ai.service.request.wx;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class WXUser {
    @SerializedName("openid")
    private String openid;
    private String nickname;
//    private String headImgUrl;
//    private String sex;
//    private String country;
//    private String province;
//    private String city;
//    private String privilege;
}
