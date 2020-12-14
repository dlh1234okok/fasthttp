package com.mani.fasthttp;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({HttpServiceRegistryBeanConfigure.class})
public @interface EnableHttpClient {
}
