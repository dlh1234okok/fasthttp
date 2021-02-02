package com.mani.fasthttp;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({HttpServiceRegister.class})
public @interface EnableHttpClient {

    String[] scanPackages() default {};

}
