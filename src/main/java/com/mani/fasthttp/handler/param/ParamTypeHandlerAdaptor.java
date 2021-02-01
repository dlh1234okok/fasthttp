package com.mani.fasthttp.handler.param;


import java.util.Map;

/**
 * @author Dulihong
 * @since 2020-12-15
 */
public interface ParamTypeHandlerAdaptor {

    boolean supports(Object var);

    Map<String, Object> handle(String name, Object value);

}
