package com.mani.fasthttp.handler.param;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Dulihong
 * @since 2020-12-15
 */
public interface ParamTypeHandlerAdaptor {

    boolean supports(Object var);

    default Map<String, Object> handle(String name, Object value) {
        Map<String, Object> map = new HashMap<>(2);
        map.put(name, value);
        return map;
    }

}
