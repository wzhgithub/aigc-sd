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

        public MiniProgramRequestMessage(String touser, String title) {
            this.touser = touser;
            this.msgtype = "miniprogrampage";
            this.miniprogrampage = new MiniprogrampageDTO(title);
        }

        @SerializedName("touser")
        private String touser;
        @SerializedName("msgtype")
        private String msgtype;
        @SerializedName("miniprogrampage")
        private MiniprogrampageDTO miniprogrampage;

        @NoArgsConstructor
        @Data
        public static class MiniprogrampageDTO {

            public MiniprogrampageDTO(String title) {
                this.title = title;
                // todo
                this.pagepath = "/pages/index/index";
                this.thumbMediaId = "1234567890"; // 图文消息的封面图片素材id
            }

            @SerializedName("title")
            private String title;
            @SerializedName("pagepath")
            private String pagepath;
            @SerializedName("thumb_media_id")
            private String thumbMediaId;
        }
    }
}
