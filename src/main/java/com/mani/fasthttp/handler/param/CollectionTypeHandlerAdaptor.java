package com.mani.fasthttp.handler.param;


import com.mani.fasthttp.annotations.Order;

import java.util.Collection;

/**
 * @author Dulihong
 * @since 2021-01-07
 */
@Order(4)
public class CollectionTypeHandlerAdaptor implements ParamTypeHandlerAdaptor {
    @Override
    public boolean supports(Object var) {
        return var instanceof Collection;
    }

    /*@Override
    public Map<String, Object> handle(String name, Object value) {
        Map<String, Object> result = new HashMap<>();
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(value));
        jsonObject.forEach((k, v) -> {
            Map<String, Object> map = RequestParamAdaptor.handlerInvoke(k, v);
            result.putAll(map);
        });
        return result;
    }*/
}
