package com.mani.fasthttp.annotations;


import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PathVariable {

    String name() default "";

    boolean required() default true;

}
