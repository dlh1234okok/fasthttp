package com.mani.fasthttp.annotations;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DeleteMapping {

    String server() default "";

    String url() default "";

    boolean allowException() default false;

    boolean async() default false;

    String formatData() default "";
}
