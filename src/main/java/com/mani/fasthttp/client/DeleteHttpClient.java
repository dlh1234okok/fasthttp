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
public class DeleteHttpClient extends HttpClient {


    public DeleteHttpClient(RequestBean requestBean) {
        super(requestBean);
    }

    @Override
    public String execute(String url) {
        HttpRequest request = HttpRequest.delete(url);
        requestParams(request);
        authorization(request);
        HttpResponse response;
        try {
            response = request.execute();
            int status = response.getStatus();
            if (HttpStatus.HTTP_OK == status) {
                return response.body();
            }
            if (requestBean.isAllowException()) {
                log.error("fasthttp请求[{}]失败,请求方式[{}],状态码：[{}]", url, "DELETE", status);
                return null;
            }
            throw new HttpRequestException("fast_http请求[" + url + "]失败,请求方式[DELETE],状态码：[" + status + "]");
        } catch (Exception e) {
            log.error("fasthttp请求[{}]异常,请求方式[{}],异常信息：[{}]", url, "DELETE", e.getMessage());
            if (requestBean.isAllowException()) {
                return null;
            } else {
                throw new HttpRequestException("fasthttp请求[" + url + "]异常,请求方式[DELETE],异常信息：：[" + e.getMessage() + "]");
            }
        }
    }
}
