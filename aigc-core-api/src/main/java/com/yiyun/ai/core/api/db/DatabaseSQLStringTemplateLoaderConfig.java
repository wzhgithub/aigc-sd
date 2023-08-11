package com.yiyun.ai.core.api.db;

import com.yiyun.ai.core.api.business.wx.CloudDatabase;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;


@ConfigurationProperties(prefix = "wx.cloud.db.sql")
public class DatabaseSQLStringTemplateLoaderConfig implements InitializingBean {
    private final Configuration configuration = new Configuration(Configuration.VERSION_2_3_32);
    @Getter
    @Setter
    Map<DataBaseOption, String> templates;

    private String getTemplate(String name, Map<String, Object> data) throws IOException, TemplateException {
        StringWriter stringWriter = new StringWriter();
        configuration.getTemplate(name).process(data, stringWriter);
        return stringWriter.toString();
    }

    public CloudDatabase.QueryOrUpdateDatabaseReq newCloudDatabaseReq(String env, Map<String, Object> data)
            throws TemplateException, IOException {
        String query = getTemplate(DataBaseOption.Query.name(), data);
        return new CloudDatabase.QueryOrUpdateDatabaseReq(env, query);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        for (Map.Entry<DataBaseOption, String> entry : templates.entrySet()) {
            stringTemplateLoader.putTemplate(entry.getKey().name(), entry.getValue());
        }
        configuration.setTemplateLoader(stringTemplateLoader);
    }
}