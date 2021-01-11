package com.mani.fasthttp.client;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;


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

    protected void body(HttpRequest request) {
        if (null != requestBean.getRequestParams()) {
            request.body(JSON.toJSONString(requestBean.getRequestParams()), "application/json;charset=utf-8");
        }
    }

    protected void applicationJSON(HttpRequest request) {
        request.contentType("application/json;charset=utf-8");
    }

    protected void authorization(HttpRequest request) {
        if (null != requestBean.getAuthorization()) {
            request.header("Authorization", requestBean.getAuthorization());
        }
    }


}
