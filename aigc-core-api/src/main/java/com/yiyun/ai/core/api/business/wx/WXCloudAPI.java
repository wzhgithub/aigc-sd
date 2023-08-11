package com.yiyun.ai.core.api.business.wx;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.Response;

import java.io.File;
import java.net.URI;

public interface WXCloudAPI {

    @RequestLine("POST /tcb/databaseupdate?access_token={access_token}")
    CloudDatabase.UpdateDatabaseResponse updateDatabase(
            CloudDatabase.QueryOrUpdateDatabaseReq request,
            @Param("access_token") String access_token
    );

    @RequestLine("POST /tcb/databasequery?access_token={access_token}")
    CloudDatabase.QueryDatabaseResponse queryDatabase(
            CloudDatabase.QueryOrUpdateDatabaseReq request,
            @Param("access_token") String access_token
    );

    @RequestLine("POST /tcb/batchdownloadfile?access_token={access_token}")
    CloudFile.CloudFileDownloadResponse batchDownloadFile(
            CloudFile.CloudFileDownloadResponse request,
            @Param("access_token") String access_token
    );


    @RequestLine("POST /tcb/uploadfile?access_token={access_token}")
    CloudFile.CloudUploadFileResponse uploadFileRequest(
            CloudFile.CloudUploadFileReq request,
            @Param("access_token") String access_token
    );

    @RequestLine("POST")
    @Headers({
            "Content-Type: multipart/form-data",
            "Signature:{sign}",
            "x-cos-security-token:{token}",
            "x-cos-meta-fileid:{file_id}"
    })
    Response uploadFileToCos(URI basicUrl,
                             @Param("file") File file,
                             @Param("sign") String sign,
                             @Param("token") String token,
                             @Param("file_id") String fileId);
}
