package com.mani.fasthttp.handler.result;

import com.mani.fasthttp.ext.ResultTypeException;
import org.springframework.util.StringUtils;

/**
 * @author Dulihong
 * @since 2020-12-11
 */
public class ResultTypeFactory {

    public static ResultTypeAdaptor getResultTypeAdaptor(String formatData) {
        if (StringUtils.isEmpty(formatData)) {
            return new DefaultResultTypeAdaptor();
        }
        String[] rules = formatData.split("\\.");
        if (rules.length < 1) {
            throw new ResultTypeException("Mapping FormatData 格式错误：[format.data]");
        }

        return new FormatResultTypeAdaptor(rules);
    }

}
