package com.mani.fasthttp.handler.param;

import cn.hutool.core.map.MapUtil;
import java.util.Map;

/**
 * @author Dulihong
 * @since 2020-12-15
 */
public interface ParamTypeHandlerAdaptor {

    boolean supports(Object var);

    default Map<String, Object> handle(String name, Object value) {
        return MapUtil.builder(name, value).build();
    }

}
