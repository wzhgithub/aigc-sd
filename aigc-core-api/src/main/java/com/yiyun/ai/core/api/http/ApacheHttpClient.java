package com.yiyun.ai.core.api.http;

import feign.Client;
import feign.Request;
import feign.Response;
import feign.hc5.ApacheHttp5Client;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ApacheHttpClient implements Client {

    private final ApacheHttp5Client httpClient;
    private final Duration timeout;
    private final Request.Options options;

    public ApacheHttpClient(ApacheClientConfig apacheClientConfig) {
        log.debug("init ApacheHttpClient with config:{}", apacheClientConfig);
        RequestConfig config = RequestConfig.custom().build();
        PoolingHttpClientConnectionManager pool = PoolingHttpClientConnectionManagerBuilder.create()
                .setMaxConnPerRoute(apacheClientConfig.getMaxConnPerRoute())
                .setMaxConnTotal(apacheClientConfig.getMaxConnections())
                .build();
        CloseableHttpClient closeableHttpClient =
                HttpClients.custom().setDefaultRequestConfig(config).setConnectionManager(pool).build();
        this.httpClient = new ApacheHttp5Client(closeableHttpClient);
        this.timeout = apacheClientConfig.getRequestTimeout();
        this.options = getOptions();
    }

    private Request.Options getOptions() {
        long millis = timeout.toMillis();
        return new Request.Options(
                millis, TimeUnit.MILLISECONDS,
                millis, TimeUnit.MILLISECONDS, true);
    }

    @Override
    public Response execute(Request request, Request.Options options) throws IOException {
        return httpClient.execute(request, this.options);
    }
}
