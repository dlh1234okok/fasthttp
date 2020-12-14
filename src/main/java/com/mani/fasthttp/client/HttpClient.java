package com.mani.fasthttp.client;

import cn.hutool.http.HttpRequest;

/**
 * @author Dulihong
 * @since 2020-12-09
 */
public abstract class HttpClient {

    protected RequestBean requestBean;

    public HttpClient(RequestBean requestBean) {
        this.requestBean = requestBean;
    }

    public abstract String execute(String url);


    protected void requestParams(HttpRequest request) {
        if (null != requestBean.getRequestParams()) {
            request.form(requestBean.getRequestParams());
        }
    }

    protected void authorization(HttpRequest request) {
        if (null != requestBean.getAuthorization()) {
            request.header("Authorization", requestBean.getAuthorization());
        }
    }


}
