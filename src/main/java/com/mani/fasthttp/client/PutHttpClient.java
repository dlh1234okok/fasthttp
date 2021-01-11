package com.mani.fasthttp.client;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import com.mani.fasthttp.ext.HttpRequestException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Dulihong
 * @since 2020-12-09
 */
@Slf4j
public class PutHttpClient extends HttpClient {


    public PutHttpClient(RequestBean requestBean) {
        super(requestBean);
    }

    @Override
    public String execute(String url) {
        HttpRequest request = HttpRequest.put(url);
        body(request);
        applicationJSON(request);
        authorization(request);
        HttpResponse response;
        try {
            response = request.execute();
            int status = response.getStatus();
            if (HttpStatus.HTTP_OK == status) {
                return response.body();
            }
            if (requestBean.isAllowException()) {
                log.error("fast_http请求[{}]失败,请求方式[{}],状态码：[{}]", url, "PUT", status);
                return null;
            }
            throw new HttpRequestException("fast_http请求[" + url + "]失败,请求方式[PUT],状态码：[" + status + "]");
        } catch (Exception e) {
            log.error("fast_http请求[{}]异常,请求方式[{}],异常信息：[{}]", url, "PUT", e.getMessage());
            if (requestBean.isAllowException()) {
                return null;
            } else {
                throw new HttpRequestException("fast_http请求[" + url + "]异常,请求方式[PUT],异常信息：：[" + e.getMessage() + "]");
            }
        }
    }
}
