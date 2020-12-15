package com.mani.fasthttp.handler.param;

import cn.hutool.core.util.ClassUtil;
import com.mani.fasthttp.annotations.Order;

/**
 * @author Dulihong
 * @since 2020-12-15
 */
@Order(2)
public class BasicTypeHandlerAdaptor implements ParamTypeHandlerAdaptor {


    @Override
    public boolean supports(Object var) {
        return ClassUtil.isBasicType(var.getClass());
    }

}
