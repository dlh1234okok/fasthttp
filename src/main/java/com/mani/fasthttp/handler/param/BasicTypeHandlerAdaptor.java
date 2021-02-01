package com.mani.fasthttp.handler.param;

import cn.hutool.core.util.ClassUtil;
import com.mani.fasthttp.annotations.Order;

/**
 * @author Dulihong
 * @since 2020-12-15
 */
@Order(3)
public class BasicTypeHandlerAdaptor extends AbstractParamHandlerAdaptor {


    @Override
    public boolean supports(Object var) {
        return ClassUtil.isBasicType(var.getClass());
    }

}
