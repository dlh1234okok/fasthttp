package com.mani.fasthttp;

import com.mani.fasthttp.config.FastHttpProperties;
import com.mani.fasthttp.constant.Constant;
import com.mani.fasthttp.proxy.ProxyFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Dulihong
 * @since 2021-02-02
 */
@Slf4j
@Component
public class HttpServiceRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private Environment environment;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(EnableHttpClient.class.getName()));

        if (null == annotationAttributes) {
            return;
        }

        HttpServiceEnableHandler enableHandler = new HttpServiceEnableHandler();

        BindResult<FastHttpProperties> bindResult = Binder.get(environment).bind(Constant.CONFIG_PREFIX, FastHttpProperties.class);

        bindResult.ifBound(s -> enableHandler.createRemoteServerHandler(s.getServer()));

        log.info("fasthttp调用服务已启用!");
        System.out.println(Constant.BANNER);

        List<Class<?>> classList = enableHandler.getRegisterBeanClass(annotationAttributes.getStringArray("scanPackages"));
        classList.forEach(cls -> register(cls, registry));
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
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

}
