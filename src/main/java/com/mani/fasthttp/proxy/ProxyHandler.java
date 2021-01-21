package com.mani.fasthttp.proxy;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import com.mani.fasthttp.handler.HttpRequestHandler;
import com.mani.fasthttp.handler.result.ResultTypeAdaptor;
import com.mani.fasthttp.handler.result.ResultTypeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

@Slf4j
public class ProxyHandler implements InvocationHandler {

    private Class<?> interfaceClass;

    public Object bind(Class<?> cls) {
        this.interfaceClass = cls;
        return Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{interfaceClass}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Annotation[] annotations = method.getAnnotations();
        HttpRequestHandler httpRequestHandler = null;
        Class<HttpRequestHandler> requestHandlerClass = HttpRequestHandler.class;
        Set<Class<?>> classes = ClassUtil.scanPackageBySuper(requestHandlerClass.getPackage().getName(), requestHandlerClass);
        for (Annotation annotation : annotations) {
            httpRequestHandler = httpRequestHandlerBuild(classes, annotation);
        }

        if (null != httpRequestHandler) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            httpRequestHandler.setAuthorization(request.getHeader("Authorization"));
            httpRequestHandler.initRequest(method.getParameters(), args);
            String result = httpRequestHandler.execute();
            if (StringUtils.isEmpty(result)) {
                log.error("lrms_http,Result is Empty,Url:[{}]", httpRequestHandler.getUrl());
                return null;
            }
            Class<?> returnType = method.getReturnType();
            ResultTypeAdaptor resultTypeAdaptor = resultTypeAdaptorBuild(httpRequestHandler, result, returnType);
            return resultTypeAdaptor.typeConversion();
        }
        return null;
    }

    private ResultTypeAdaptor resultTypeAdaptorBuild(HttpRequestHandler httpRequestHandler, String result, Class<?> returnType) {
        ResultTypeAdaptor resultTypeAdaptor = ResultTypeFactory.getResultTypeAdaptor(httpRequestHandler.getFormatData());
        resultTypeAdaptor.setResultJson(result);
        resultTypeAdaptor.setReturnType(returnType);
        resultTypeAdaptor.setGeneric(httpRequestHandler.getGeneric());
        resultTypeAdaptor.setAllowException(httpRequestHandler.isAllowException());
        return resultTypeAdaptor;
    }

    private HttpRequestHandler httpRequestHandlerBuild(Set<Class<?>> classes, Annotation annotation) {
        HttpRequestHandler httpRequestHandler = null;
        for (Class<?> cls : classes) {
            Class<HttpRequestHandler> clz = (Class<HttpRequestHandler>) cls;
            HttpRequestHandler requestHandler = ReflectUtil.newInstanceIfPossible(clz);
            if (requestHandler.support(annotation)) {
                httpRequestHandler = requestHandler.builder(annotation);
                break;
            }
        }
        return httpRequestHandler;
    }


}