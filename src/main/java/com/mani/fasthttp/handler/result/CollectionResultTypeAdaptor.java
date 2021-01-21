package com.mani.fasthttp.handler.result;

import com.alibaba.fastjson.JSON;

import java.util.Collection;

/**
 * @author Dulihong
 * @since 2021-01-21
 */
public class CollectionResultTypeAdaptor extends ResultTypeAdaptor {

    public boolean isCollection(Object o) {
        return o instanceof Collection;
    }

    @Override
    public Object typeConversion() {
        return super.typeConversion();
    }


    public Collection<?> toCollection(Object o, Class<?> generic) {
        return JSON.parseArray(JSON.toJSONString(o), generic);
    }


}
