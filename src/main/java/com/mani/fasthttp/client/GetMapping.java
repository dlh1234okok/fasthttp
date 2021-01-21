package com.mani.fasthttp.client;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GetMapping {

    String server() default "";

    String url() default "";

    /**
     * 允许异常不影响程序
     */
    boolean allowException() default false;

    /**
     * 发送完请求不直接读响应内容，等有需要的时候读
     */
    boolean readAsync() default false;

    String formatData() default "";

    Class<?> generic() default Void.class;
}
