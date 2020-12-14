package com.mani.fasthttp.proxy;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author Dulihong
 * @since 2020-12-09
 */
public class ProxyFactory<T> implements FactoryBean<T> {

    private Class<T> interfaceClass;

    public Class<T> getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(Class<T> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    @Override
    public T getObject() throws Exception {
        return (T) new ProxyHandler().bind(interfaceClass);
    }

    @Override
    public Class<?> getObjectType() {
        return interfaceClass;
    }

    @Override
    public boolean isSingleton() {
        // 单例模式
        return true;
    }

}