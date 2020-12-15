package com.mani.fasthttp.config;

import com.mani.fasthttp.HttpServiceRegistryBeanConfigure;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Dulihong
 * @since 2020-12-10
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(FastHttpProperties.class)
@ConditionalOnBean({HttpServiceRegistryBeanConfigure.class})
public class FastHttpAutoConfigure {
    @Autowired
    private FastHttpProperties fastHttpProperties;


}
