package com.yiyun.ai.core.api.business.wx;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class CloudFile {

    @NoArgsConstructor
    @Data
    public static class CloudUploadFileReq {

        @SerializedName("env")
        private String env;
        @SerializedName("path")
        private String path;
    }

    @NoArgsConstructor
    @Data
    public static class CloudUploadFileResponse {

        @SerializedName("errcode")
        private Integer errcode;
        @SerializedName("errmsg")
        private String errmsg;
        @SerializedName("url")
        private String url;
        @SerializedName("token")
        private String token;
        @SerializedName("authorization")
        private String authorization;
        @SerializedName("file_id")
        private String fileId;
        @SerializedName("cos_file_id")
        private String cosFileId;
    }

    @NoArgsConstructor
    @Data
    public static class CloudFileDownloadReq {

        @SerializedName("env")
        private String env;
        @SerializedName("file_list")
        private List<FileListDTO> fileList;

        @NoArgsConstructor
        @Data
        public static class FileListDTO {
            @SerializedName("fileid")
            private String fileid;
            @SerializedName("max_age")
            private Long maxAge;
        }
    }

    @NoArgsConstructor
    @Data
    public static class CloudFileDownloadResponse {

        @SerializedName("errcode")
        private Integer errcode;
        @SerializedName("errmsg")
        private String errmsg;
        @SerializedName("file_list")
        private List<FileListDTO> fileList;

        @NoArgsConstructor
        @Data
        public static class FileListDTO {
            @SerializedName("fileid")
            private String fileid;
            @SerializedName("download_url")
            private String downloadUrl;
            @SerializedName("status")
            private Long status;
            @SerializedName("errmsg")
            private String errmsg;
        }
    }
}
