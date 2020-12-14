package com.mani.fasthttp.handler;

import com.mani.fasthttp.annotations.DeleteMapping;
import com.mani.fasthttp.client.DeleteHttpClient;
import com.mani.fasthttp.client.HttpClient;
import com.mani.fasthttp.client.RequestBean;

import java.lang.annotation.Annotation;

/**
 * @author Dulihong
 * @since 2020-12-09
 */
public class DeleteRequestHandler extends HttpRequestHandler {

    public DeleteRequestHandler() {
    }

    public DeleteRequestHandler(String server, String url) {
        super(server, url);
    }

    @Override
    public boolean support(Annotation annotation) {
        return annotation instanceof DeleteMapping;
    }

    @Override
    public HttpRequestHandler builder(Annotation annotation) {
        DeleteMapping deleteMapping = (DeleteMapping) annotation;
        HttpRequestHandler httpRequestHandler = new DeleteRequestHandler(deleteMapping.server(), deleteMapping.url());
        httpRequestHandler.setAllowException(deleteMapping.allowException());
        httpRequestHandler.setAsync(deleteMapping.async());
        httpRequestHandler.setFormatData(deleteMapping.formatData());
        return httpRequestHandler;
    }

    @Override
    public String execute() {
        RequestBean requestBean = new RequestBean();
        requestBean.setAllowException(allowException);
        requestBean.setAuthorization(Authorization);
        requestBean.setRequestParams(params);
        HttpClient httpClient = new DeleteHttpClient(requestBean);
        return execute(httpClient);
    }
}
