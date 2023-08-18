package com.yiyun.ai.core.api.business.wx;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

public class CloudMessage {

    @NoArgsConstructor
    @Data
    public static class MessageResponse {

        @SerializedName("errcode")
        private Integer errcode;
        @SerializedName("errmsg")
        private String errmsg;
    }

    @NoArgsConstructor
    @Data
    public static class MiniProgramRequestMessage {

        @SerializedName("touser")
        private String touser;
        @SerializedName("msgtype")
        private String msgtype;
        @SerializedName("miniprogrampage")
        private MiniprogrampageDTO miniprogrampage;

        @NoArgsConstructor
        @Data
        public static class MiniprogrampageDTO {
            @SerializedName("title")
            private String title;
            @SerializedName("pagepath")
            private String pagepath;
            @SerializedName("thumb_media_id")
            private String thumbMediaId;
        }
    }
}
