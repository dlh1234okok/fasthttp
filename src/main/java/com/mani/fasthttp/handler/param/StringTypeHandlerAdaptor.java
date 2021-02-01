package com.mani.fasthttp.handler.param;


import com.mani.fasthttp.annotations.Order;

/**
 * @author Dulihong
 * @since 2020-12-15
 */
@Order(1)
public class StringTypeHandlerAdaptor extends AbstractParamHandlerAdaptor {

    @Override
    public boolean supports(Object var) {
        return var instanceof String;
    }

}
