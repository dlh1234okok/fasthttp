package com.mani.fasthttp.handler;

import com.mani.fasthttp.annotations.PostMapping;
import com.mani.fasthttp.client.HttpClient;
import com.mani.fasthttp.client.PostHttpClient;
import com.mani.fasthttp.client.RequestBean;

import java.lang.annotation.Annotation;

/**
 * @author Dulihong
 * @since 2020-12-09
 */
public class PostRequestHandler extends HttpRequestHandler {

    public PostRequestHandler() {
    }

    public PostRequestHandler(String server, String url) {
        super(server, url);
    }

    @Override
    public boolean support(Annotation annotation) {
        return annotation instanceof PostMapping;
    }

    @Override
    public HttpRequestHandler builder(Annotation annotation) {
        PostMapping postMapping = (PostMapping) annotation;
        HttpRequestHandler httpRequestHandler = new PostRequestHandler(postMapping.server(), postMapping.url());
        httpRequestHandler.setAllowException(postMapping.allowException());
        httpRequestHandler.setAsync(postMapping.async());
        httpRequestHandler.setFormatData(postMapping.formatData());
        return httpRequestHandler;
    }

    @Override
    public String execute() {
        RequestBean requestBean = new RequestBean();
        requestBean.setAllowException(allowException);
        requestBean.setAuthorization(Authorization);
        requestBean.setRequestParams(params);
        HttpClient httpClient = new PostHttpClient(requestBean);
        return execute(httpClient);
    }
}
