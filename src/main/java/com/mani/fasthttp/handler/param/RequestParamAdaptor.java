package com.mani.fasthttp.handler.param;

import com.mani.fasthttp.annotations.PathVariable;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.util.LinkedHashMap;
import java.util.Map;

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
                params.put(parameter.getName(), args[i]);
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
}
