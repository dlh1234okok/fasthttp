package com.mani.fasthttp;

import com.mani.fasthttp.annotations.Service;
import com.mani.fasthttp.constant.Constant;
import com.mani.fasthttp.handler.ReadAnnotationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Dulihong
 * @since 2021-02-02
 */
@Slf4j
public class HttpServiceEnableHandler {

    private String[] scanPackages;


    public String[] getScanPackages() {
        return scanPackages;
    }

    public void createRemoteServerHandler(Map<String, String> server) {
        RemoteServerHandler.initServer(server);
    }

    public List<Class<?>> getRegisterBeanClass(String[] scanPackages) {
        this.scanPackages = scanPackages;
        List<Class<?>> clz = Arrays.stream(scanPackages).flatMap(s -> ReadAnnotationUtils.getClazzFromAnnotation(s, Service.class).stream()).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(clz)) {
            log.info("no class to register");
        }
        return clz;
    }


    /*private String[] getDefaultScanPackages(ApplicationContext ctx) {
        String[] defaultScanPackages = new String[]{ReadAnnotationUtils.getPackageByAnnotation(ctx, SpringBootApplication.class) + Constant.DEFAULT_PACKAGE};
        log.info("scan defaultPackages：[{}]", String.join(",", defaultScanPackages));
        return defaultScanPackages;
    }*/
}
