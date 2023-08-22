package com.yiyun.ai.core.api.business.sd;

import com.google.common.collect.ImmutableMap;
import com.yiyun.ai.core.api.http.AbstractFeignHttpConfig;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

@Data
@ConfigurationProperties("sd.config")
public class SDServerlessConfig implements InitializingBean {

    @NestedConfigurationProperty
    SDText2ImageConfig txt2img;
    @NestedConfigurationProperty
    SDServerHttpConfig http;
    @NestedConfigurationProperty
    SDServerWSConfig ws;

    @Override
    public void afterPropertiesSet() throws Exception {
        txt2img.afterPropertiesSet();
    }

    @Data
    @Slf4j
    public static class SDText2ImageConfig implements InitializingBean {
        String prompt;
        String sample;
        String model;
        String requestTemplate;
        String templateName;
        transient private Configuration cfg = null;

        public String newTxt2ImageJsonString(String imageBase64) {
            try {
                Template template = cfg.getTemplate(templateName);
                StringWriter sw = new StringWriter();
                template.process(ImmutableMap.of("base64SrcImg", imageBase64), sw);
                return sw.toString();
            } catch (IOException | TemplateException e) {
                log.error("newTxt2ImageJsonString error", e);
                return "";
            }
        }

        @Override
        public void afterPropertiesSet() throws Exception {
            if (Objects.nonNull(cfg)) return;
            log.debug("load cfg template with path:{}", requestTemplate);
            cfg = getConfiguration();
        }

        private Configuration getConfiguration() {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);

            // Specify the source where the template files come from. Here I set a
            // plain directory for it, but non-file-system sources are possible too:
            cfg.setClassForTemplateLoading(this.getClass(), requestTemplate);

// From here we will set the settings recommended for new projects. These
// aren't the defaults for backward compatibilty.

// Set the preferred charset template files are stored in. UTF-8 is
// a good choice in most applications:
            cfg.setDefaultEncoding("UTF-8");

// Sets how errors will appear.
// During web page *development* TemplateExceptionHandler.HTML_DEBUG_HANDLER is better.
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

// Don't log exceptions inside FreeMarker that it will thrown at you anyway:
            cfg.setLogTemplateExceptions(false);

// Wrap unchecked exceptions thrown during template processing into TemplateException-s:
            cfg.setWrapUncheckedExceptions(true);

// Do not fall back to higher scopes when reading a null loop variable:
            cfg.setFallbackOnNullLoopVariable(false);

// To accomodate to how JDBC returns values; see Javadoc!
            cfg.setSQLDateAndTimeTimeZone(TimeZone.getDefault());
            return cfg;
        }
    }

    @Data
    public static class SDServerWSConfig {
        String url;

        public SDWebSocketClient newClient(List<AbstractSDWSJsonHandler> handlers)
                throws URISyntaxException {
            SDWebSocketClient sdWebSocketClient = new SDWebSocketClient(new URI(url), handlers);
            sdWebSocketClient.connect();
            return sdWebSocketClient;
        }
    }

    public static class SDServerHttpConfig extends AbstractFeignHttpConfig<SDServerlessAPI> {
    }
}
