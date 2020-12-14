package com.mani.fasthttp.client;

import lombok.Data;

import java.util.Map;

/**
 * @author Dulihong
 * @since 2020-12-14
 */
@Data
public class RequestBean {

    private String server;
    private String url;
    private String Authorization;
    private boolean allowException;
    private boolean async = false;
    private boolean readAsync;
    private String formatData;

    private Map<String, Object> requestParams;

}
