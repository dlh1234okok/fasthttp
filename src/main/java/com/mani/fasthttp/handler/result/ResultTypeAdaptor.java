package com.mani.fasthttp.handler.result;

import com.alibaba.fastjson.JSON;
import com.mani.fasthttp.ext.ResultTypeException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Dulihong
 * @since 2020-12-11
 */
@Slf4j
public abstract class ResultTypeAdaptor {

    protected String resultJson;
    protected Class<?> returnType;
    protected boolean allowException;

    protected Class<?> generic;


    public Object typeConversion() {
        try {
            return finalResult(resultJson);
        } catch (Exception e) {
            if (allowException) {
                log.error("Get Result Error,ResultType：[{}]", returnType.getName());
                return null;
            }
            throw new ResultTypeException("Get Result Error,ResultType：[" + returnType.getName() + "]");
        }
    }


    protected Object finalResult(String json) {
        CollectionResultTypeAdaptor collectionResultTypeAdaptor = new CollectionResultTypeAdaptor();
        Object result = JSON.parseObject(json, returnType);
        if (collectionResultTypeAdaptor.isCollection(result)) {
            result = collectionResultTypeAdaptor.toCollection(result, generic);
        }
        return result;
    }


    public void setResultJson(String resultJson) {
        this.resultJson = resultJson;
    }

    public void setReturnType(Class<?> returnType) {
        this.returnType = returnType;
    }

    public void setAllowException(boolean allowException) {
        this.allowException = allowException;
    }

    public void setGeneric(Class<?> generic) {
        this.generic = generic;
    }
}
