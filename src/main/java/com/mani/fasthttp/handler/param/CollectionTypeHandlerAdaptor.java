package com.mani.fasthttp.handler.param;


import com.mani.fasthttp.annotations.Order;

import java.util.Collection;

/**
 * @author Dulihong
 * @since 2021-01-07
 */
@Order(4)
public class CollectionTypeHandlerAdaptor extends AbstractParamHandlerAdaptor {
    @Override
    public boolean supports(Object var) {
        return var instanceof Collection;
    }

}
