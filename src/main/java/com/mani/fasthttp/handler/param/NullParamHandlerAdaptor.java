package com.mani.fasthttp.handler.param;


import com.mani.fasthttp.annotations.Order;

/**
 * @author Dulihong
 * @since 2020-12-15
 */
@Order(0)
public class NullParamHandlerAdaptor implements ParamTypeHandlerAdaptor {

    @Override
    public boolean supports(Object var) {
        return null == var;
    }

}
