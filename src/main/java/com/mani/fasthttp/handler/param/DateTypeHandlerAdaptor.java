package com.mani.fasthttp.handler.param;


import com.mani.fasthttp.annotations.Order;

import java.util.Date;

/**
 * @author Dulihong
 * @since 2021-01-07
 */
@Order(5)
public class DateTypeHandlerAdaptor extends AbstractParamHandlerAdaptor {

    @Override
    public boolean supports(Object var) {
        return var instanceof Date;
    }

}
