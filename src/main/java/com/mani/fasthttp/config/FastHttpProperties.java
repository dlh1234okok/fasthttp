package com.mani.fasthttp.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @author Dulihong
 * @since 2020-12-10
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "fast.http")
public class FastHttpProperties {

    private Map<String, String> server;

    private String scanPackages;

    private String authorization;

}