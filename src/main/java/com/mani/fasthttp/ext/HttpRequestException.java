package com.mani.fasthttp.ext;

/**
 * @author Dulihong
 * @since 2020-12-09
 */
public class HttpRequestException extends RuntimeException {

    public HttpRequestException() {
    }

    public HttpRequestException(String message) {
        super(message);
    }
}
