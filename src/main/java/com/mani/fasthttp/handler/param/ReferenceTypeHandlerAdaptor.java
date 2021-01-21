package com.mani.fasthttp.handler.param;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import com.mani.fasthttp.annotations.Order;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Dulihong
 * @since 2020-12-15
 */
@Order(99)
public class ReferenceTypeHandlerAdaptor implements ParamTypeHandlerAdaptor {
    @Override
    public boolean supports(Object var) {
        return !ClassUtil.isBasicType(var.getClass());
    }

    @Override
    public Map<String, Object> handle(String name, Object value) {
        Map<String, Object> result = new HashMap<>();

        for (Field declaredField : getBeanFields(value.getClass(), null)) {
            Object fieldValue = ReflectUtil.getFieldValue(value, declaredField);
            // Map<String, Object> map = RequestParamAdaptor.handlerInvoke(declaredField.getName(), fieldValue);
            result.put(declaredField.getName(), fieldValue);
        }
        return result;
    }

    public Field[] getBeanFields(Class<?> cls, Field[] fs) {

        fs = ArrayUtil.addAll(fs, cls.getDeclaredFields());

        if (cls.getSuperclass() != null) {

            Class<?> clsSup = cls.getSuperclass();

            fs = getBeanFields(clsSup, fs);

        }

        return fs;

    }
}
