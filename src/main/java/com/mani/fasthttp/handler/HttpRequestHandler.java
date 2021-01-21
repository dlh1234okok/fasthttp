package com.mani.fasthttp.handler;



import com.mani.fasthttp.client.HttpClient;
import com.mani.fasthttp.config.ExecutorThread;
import com.mani.fasthttp.handler.param.RequestParamAdaptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * @author Dulihong
 * @since 2020-12-09
 */
public abstract class HttpRequestHandler {

    protected String server;
    protected String url;
    protected String Authorization;
    protected boolean allowException;
    protected boolean async = false;
    protected boolean readAsync;
    protected String formatData;

    protected Class<?> generic;

    protected Map<String, Object> params;

    public HttpRequestHandler() {
    }

    public HttpRequestHandler(String server, String url) {
        this.server = server;
        this.url = url;
    }

    public abstract boolean support(Annotation annotation);

    public abstract HttpRequestHandler builder(Annotation annotation);

    public abstract String execute();

    protected String execute(HttpClient httpClient) {
        if (async) {
            ExecutorService instance = ExecutorThread.getInstance();
            instance.execute(() -> {
                httpClient.execute(RemoteServerHandler.getServerPath(server) + url);
            });
            return null;
        } else {
            return httpClient.execute(RemoteServerHandler.getServerPath(server) + url);
        }
    }


    public void initRequest(Parameter[] parameters, Object[] args) {
        RequestParamAdaptor requestParamAdaptor = new RequestParamAdaptor(parameters, args);
        requestParamAdaptor.init(url);
        this.url = requestParamAdaptor.getUrl();
        this.params = requestParamAdaptor.getParams();
    }

    public void setAuthorization(String authorization) {
        Authorization = authorization;
    }

    public void setAllowException(boolean allowException) {
        this.allowException = allowException;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }

    public void setReadAsync(boolean readAsync) {
        this.readAsync = readAsync;
    }

    public void setFormatData(String formatData) {
        this.formatData = formatData;
    }

    public String getFormatData() {
        return formatData;
    }

    public boolean isAllowException() {
        return allowException;
    }

    public String getUrl() {
        return url;
    }

    public Class<?> getGeneric() {
        return generic;
    }

    public void setGeneric(Class<?> generic) {
        this.generic = generic;
    }
}
