package com.mani.fasthttp.handler.param;

import cn.hutool.core.map.MapUtil;

import java.util.Map;

/**
 * @author Dulihong
 * @since 2021-02-01
 */
public abstract class AbstractParamHandlerAdaptor implements ParamTypeHandlerAdaptor {

    public AbstractParamHandlerAdaptor nextHandlerAdaptor;

    public static AbstractParamHandlerAdaptor handlerAdaptor;

    public static AbstractParamHandlerAdaptor getHandlerAdaptorChain() {
        if (null == handlerAdaptor) {
            handlerAdaptor = new NullParamHandlerAdaptor();
            handlerAdaptor
                    .setNextHandlerAdaptor(new StringTypeHandlerAdaptor())
                    .setNextHandlerAdaptor(new EnumTypeHandlerAdaptor())
                    .setNextHandlerAdaptor(new BasicTypeHandlerAdaptor())
                    .setNextHandlerAdaptor(new CollectionTypeHandlerAdaptor())
                    .setNextHandlerAdaptor(new DateTypeHandlerAdaptor())
                    .setNextHandlerAdaptor(new ReferenceTypeHandlerAdaptor());
        }
        return handlerAdaptor;
    }


    public Map<String, Object> handle(String name, Object value) {
        return MapUtil.builder(name, value).build();
    }


    public Map<String, Object> process(String name, Object value) {
        AbstractParamHandlerAdaptor handlerAdaptor = this;
        do {
            if (handlerAdaptor.supports(value)) {
                return handlerAdaptor.handle(name, value);
            }
            handlerAdaptor = handlerAdaptor.nextHandlerAdaptor;
        } while (handlerAdaptor != null);
        return null;
    }

    public AbstractParamHandlerAdaptor setNextHandlerAdaptor(AbstractParamHandlerAdaptor nextHandlerAdaptor) {
        this.nextHandlerAdaptor = nextHandlerAdaptor;
        return nextHandlerAdaptor;
    }
}
