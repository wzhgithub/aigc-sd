package com.yiyun.ai.service.request.wx;

import com.google.common.collect.ImmutableMap;
import com.yiyun.ai.core.api.business.wx.CloudDatabase;
import com.yiyun.ai.core.api.db.DataBaseOption;
import com.yiyun.ai.core.api.db.DatabaseSQLStringTemplateLoaderConfig;
import com.yiyun.ai.service.sd.SDTask;
import freemarker.template.TemplateException;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.Map;

@Setter
@Getter
public class WXQCRGenRequest extends AbstractWXAIGCRequest {

    public CloudDatabase.QueryOrUpdateDatabaseReq newUpdateQuery(
            SDTask.TaskStatus status,
            DatabaseSQLStringTemplateLoaderConfig config,
            String env) throws TemplateException, IOException {
        return config.newCloudDatabaseReq(DataBaseOption.Update, env,
                ImmutableMap.of("collection", collection,
                        "condition", String.format("{%s:%s}", "task_id", taskId),
                        "update", String.format("{%s:%s}", "status", status.name())));
    }

    public Map<String, Object> dbArgs() {
        return ImmutableMap.of("collection", collection, "limit", 1, "condition",
                String.format("{%s:%s}", "task_id", taskId));
    }
}
