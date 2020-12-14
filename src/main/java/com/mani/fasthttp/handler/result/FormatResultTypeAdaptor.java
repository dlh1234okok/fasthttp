package com.mani.fasthttp.handler.result;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mani.fasthttp.ext.ResultTypeException;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Iterator;

/**
 * @author Dulihong
 * @since 2020-12-11
 */
@Slf4j
public class FormatResultTypeAdaptor extends ResultTypeAdaptor {

    private String[] rules;

    public FormatResultTypeAdaptor(String[] rules) {
        this.rules = rules;
    }

    @Override
    public Object typeConversion() {
        try {
            JSONObject init = JSON.parseObject(resultJson);
            Object result = new Object();
            Iterator<String> iterator = Arrays.asList(rules).iterator();
            while (iterator.hasNext()) {
                String next = iterator.next();
                Object o = init.get(next);
                result = o;
                if (iterator.hasNext()) {
                    init = JSON.parseObject(JSON.toJSONString(o));
                }
            }
            return result;
        } catch (Exception e) {
            if (allowException) {
                log.error("Format Data ERROR，Rules：[{}]", String.join(",", rules), e);
                return null;
            }
            throw new ResultTypeException("Format Data ERROR，Rules：[" + String.join(",", rules) + "]");
        }
    }
}
