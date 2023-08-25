package com.yiyun.ai.service.request.wx;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public abstract class AbstractWXAIGCRequest {
    @SerializedName("user")
    protected WXUser user;
    // 任务ID
    @SerializedName("task_id")
    protected String taskId;
    @SerializedName("collection")
    protected String collection;
}
