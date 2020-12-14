package com.mani.fasthttp.handler;

import com.mani.fasthttp.annotations.PutMapping;
import com.mani.fasthttp.client.HttpClient;
import com.mani.fasthttp.client.PutHttpClient;
import com.mani.fasthttp.client.RequestBean;

import java.lang.annotation.Annotation;

/**
 * @author Dulihong
 * @since 2020-12-09
 */
public class PutRequestHandler extends HttpRequestHandler {

    public PutRequestHandler() {
    }

    public PutRequestHandler(String server, String url) {
        super(server, url);
    }

    @Override
    public boolean support(Annotation annotation) {
        return annotation instanceof PutMapping;
    }

    @Override
    public HttpRequestHandler builder(Annotation annotation) {
        PutMapping putMapping = (PutMapping) annotation;
        HttpRequestHandler httpRequestHandler = new PutRequestHandler(putMapping.server(), putMapping.url());
        httpRequestHandler.setAllowException(putMapping.allowException());
        httpRequestHandler.setAsync(putMapping.async());
        httpRequestHandler.setFormatData(putMapping.formatData());
        return httpRequestHandler;
    }

    @Override
    public String execute() {
        RequestBean requestBean = new RequestBean();
        requestBean.setAllowException(allowException);
        requestBean.setAuthorization(Authorization);
        requestBean.setRequestParams(params);
        HttpClient httpClient = new PutHttpClient(requestBean);
        return execute(httpClient);
    }
}
