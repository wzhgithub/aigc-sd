package com.yiyun.ai.core.api.db.cloud;

import com.yiyun.ai.core.api.business.wx.CloudDatabase;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import java.util.TimeZone;


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

    public CloudDatabase.QueryOrUpdateDatabaseReq newCloudDatabaseReq(
            DataBaseOption option,
            String env,
            Map<String, Object> data)
            throws TemplateException, IOException {
        String query = getTemplate(option.name(), data);
        return new CloudDatabase.QueryOrUpdateDatabaseReq(env, query);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        for (Map.Entry<DataBaseOption, String> entry : templates.entrySet()) {
            stringTemplateLoader.putTemplate(entry.getKey().name(), entry.getValue());
        }
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        configuration.setLogTemplateExceptions(false);
        configuration.setWrapUncheckedExceptions(true);
        configuration.setFallbackOnNullLoopVariable(false);
        configuration.setSQLDateAndTimeTimeZone(TimeZone.getDefault());
        configuration.setTemplateLoader(stringTemplateLoader);
    }
}
