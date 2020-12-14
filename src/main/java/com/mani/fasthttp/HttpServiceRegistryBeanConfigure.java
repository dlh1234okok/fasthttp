package com.mani.fasthttp;

import com.mani.fasthttp.annotations.Service;
import com.mani.fasthttp.config.LrmsHttpProperties;
import com.mani.fasthttp.constant.Constant;
import com.mani.fasthttp.handler.ReadAnnotationUtils;
import com.mani.fasthttp.handler.RemoteServerHandler;
import com.mani.fasthttp.proxy.ProxyFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Dulihong
 * @since 2020-12-09
 */
@Component
@Slf4j
public class HttpServiceRegistryBeanConfigure implements ApplicationContextAware, BeanDefinitionRegistryPostProcessor, EnvironmentAware {

    private ApplicationContext ctx;

    private Environment environment;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        BindResult<LrmsHttpProperties> bindResult = Binder.get(environment).bind(Constant.CONFIG_PREFIX, LrmsHttpProperties.class);
        LrmsHttpProperties lrmsHttpProperties = bindResult.orElseGet(() -> {
            LrmsHttpProperties properties = new LrmsHttpProperties();
            String defaultScan = ReadAnnotationUtils.getPackageByAnnotation(ctx, SpringBootApplication.class) + Constant.DEFAULT_PACKAGE;
            properties.setScanPackages(defaultScan);
            log.info("设置默认扫描路径[{}]", defaultScan);
            return properties;
        });
        RemoteServerHandler.initServer(lrmsHttpProperties.getServer());
        RemoteServerHandler.initScanPackages(lrmsHttpProperties.getScanPackages());
        log.info("fasthttp调用服务已启用!");
        System.out.println(Constant.BANNER);
        List<String> scanPackages = getScanPackagesList(lrmsHttpProperties.getScanPackages());
        List<Class<?>> clzz = scanPackages.stream().flatMap(s -> ReadAnnotationUtils.getClazzFromAnnotation(s, Service.class).stream()).collect(Collectors.toList());
        clzz.forEach(cls -> register(cls, beanDefinitionRegistry));
    }

    public List<String> getScanPackagesList(String scanPackages) {
        return Arrays.asList(scanPackages.split(","));
    }

    private void register(Class<?> cls, BeanDefinitionRegistry beanDefinitionRegistry) {
        // 需要被代理的接口
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(cls);
        GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
        definition.getPropertyValues().add("interfaceClass", definition.getBeanClassName());
        definition.setBeanClass(ProxyFactory.class);
        definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
        // 注册bean
        String beanName = cls.getSimpleName().substring(0, 1).toLowerCase() + cls.getSimpleName().substring(1);
        log.info("httpRemoteService Register Bean：[{}]", beanName);
        beanDefinitionRegistry.registerBeanDefinition(beanName, definition);
    }

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        this.ctx = ctx;
    }


}