package com.yiyun.ai.core.api.business.wx;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class LoginResponse {

    @SerializedName("openid")
    private String openid;
    @SerializedName("session_key")
    private String sessionKey;
    @SerializedName("unionid")
    private String unionid;
    @SerializedName("errcode")
    private Integer errcode;
    @SerializedName("errmsg")
    private String errmsg;
}
