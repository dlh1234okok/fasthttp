package com.mani.fasthttp.handler.param;

import cn.hutool.core.util.ClassUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mani.fasthttp.annotations.Order;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Dulihong
 * @since 2020-12-15
 */
@Order(4)
public class ReferenceTypeHandlerAdaptor implements ParamTypeHandlerAdaptor {
    @Override
    public boolean supports(Object var) {
        return !ClassUtil.isBasicType(var.getClass());
    }

    @Override
    public Map<String, Object> handle(String name, Object value) {
        Map<String, Object> result = new HashMap<>();
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(value));
        jsonObject.forEach((k, v) -> {
            Map<String, Object> map = RequestParamAdaptor.handlerInvoke(k, v);
            result.putAll(map);
        });
        return result;
    }
}
