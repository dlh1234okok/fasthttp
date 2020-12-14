package com.mani.fasthttp.handler;

import com.mani.fasthttp.annotations.GetMapping;
import com.mani.fasthttp.client.GetHttpClient;
import com.mani.fasthttp.client.HttpClient;
import com.mani.fasthttp.client.RequestBean;

import java.lang.annotation.Annotation;

/**
 * @author Dulihong
 * @since 2020-12-09
 */
public class GetRequestHandler extends HttpRequestHandler {

    public GetRequestHandler() {
    }

    public GetRequestHandler(String server, String url) {
        super(server, url);
    }

    @Override
    public boolean support(Annotation annotation) {
        return annotation instanceof GetMapping;
    }

    @Override
    public HttpRequestHandler builder(Annotation annotation) {
        GetMapping getMapping = (GetMapping) annotation;
        HttpRequestHandler httpRequestHandler = new GetRequestHandler(getMapping.server(), getMapping.url());
        httpRequestHandler.setAllowException(getMapping.allowException());
        httpRequestHandler.setReadAsync(getMapping.readAsync());
        httpRequestHandler.setFormatData(getMapping.formatData());
        return httpRequestHandler;
    }

    @Override
    public String execute() {
        RequestBean requestBean = new RequestBean();
        requestBean.setAllowException(allowException);
        requestBean.setAuthorization(Authorization);
        requestBean.setReadAsync(readAsync);
        requestBean.setRequestParams(params);
        HttpClient httpClient = new GetHttpClient(requestBean);
        return execute(httpClient);
    }
}
