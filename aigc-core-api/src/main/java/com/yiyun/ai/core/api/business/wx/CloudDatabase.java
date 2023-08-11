package com.yiyun.ai.core.api.business.wx;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class CloudDatabase {

    @NoArgsConstructor
    @Data
    public static class UpdateDatabaseResponse {

        @SerializedName("errcode")
        private Integer errcode;
        @SerializedName("errmsg")
        private String errmsg;
        @SerializedName("matched")
        private Integer matched;
        @SerializedName("modified")
        private Integer modified;
        @SerializedName("id")
        private String id;
    }

    @NoArgsConstructor
    @Data
    public static class QueryDatabaseResponse {

        @SerializedName("errcode")
        private Integer errcode;
        @SerializedName("errmsg")
        private String errmsg;
        @SerializedName("pager")
        private PagerDTO pager;
        @SerializedName("data")
        private List<String> data; // json object str

        @NoArgsConstructor
        @Data
        public static class PagerDTO {
            @SerializedName("Offset")
            private Integer offset;
            @SerializedName("Limit")
            private Integer limit;
            @SerializedName("Total")
            private Integer total;
        }
    }

    @Data
    public static class QueryOrUpdateDatabaseReq {
        public QueryOrUpdateDatabaseReq(String env, String query) {
            this.env = env;
            this.query = query;
        }

        @SerializedName("env")
        private String env;
        @SerializedName("query")
        private String query;
    }
}
