package com.mani.fasthttp.handler.param;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import com.mani.fasthttp.annotations.Order;
import com.mani.fasthttp.annotations.PathVariable;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Dulihong
 * @since 2020-12-11
 */
public class RequestParamAdaptor {

    private Parameter[] parameters;
    private Object[] args;

    private String url;
    private Map<String, Object> params;

    public RequestParamAdaptor(Parameter[] parameters, Object[] args) {
        this.parameters = parameters;
        this.args = args;
    }

    public void init(String url) {
        Map<String, Object> params = new LinkedHashMap<>();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            Annotation[] annotations = parameter.getAnnotations();
            boolean isRest = false;
            for (Annotation annotation : annotations) {
                if (annotation instanceof PathVariable) {
                    String name = ((PathVariable) annotation).name();
                    if (StringUtils.isEmpty(name)) {
                        name = parameter.getName();
                    }
                    url = url.replaceAll("\\{" + name + "}", String.valueOf(args[i]));
                    isRest = true;
                    break;
                }
            }
            if (!isRest) {
                // params.put(parameter.getName(), args[i]);
                Optional.ofNullable(handlerInvoke(parameter.getName(), args[i])).ifPresent(params::putAll);
            }
        }
        this.url = url;
        this.params = params;
    }


    public String getUrl() {
        return url;
    }

    public Map<String, Object> getParams() {
        return params;
    }


    static List<Class<?>> getHandlerClasses(String name, Object value) {
        Class<ParamTypeHandlerAdaptor> paramTypeHandlerAdaptorClass = ParamTypeHandlerAdaptor.class;
        Set<Class<?>> classes = ClassUtil.scanPackageBySuper(paramTypeHandlerAdaptorClass.getPackage().getName(), paramTypeHandlerAdaptorClass);
        return classes.stream().sorted(Comparator.comparing(s -> {
            Order order = s.getAnnotation(Order.class);
            return order.value();
        })).collect(Collectors.toList());
    }

    static Map<String, Object> handlerInvoke(String name, Object value) {
        List<Class<?>> handlerClasses = getHandlerClasses(name, value);
        Map<String, Object> result = null;
        for (Class<?> cls : handlerClasses) {
            Class<ParamTypeHandlerAdaptor> clz = (Class<ParamTypeHandlerAdaptor>) cls;
            ParamTypeHandlerAdaptor adaptor = ReflectUtil.newInstanceIfPossible(clz);
            if (adaptor.supports(value)) {
                result = adaptor.handle(name, value);
                break;
            }
        }
        return result;
    }
}
