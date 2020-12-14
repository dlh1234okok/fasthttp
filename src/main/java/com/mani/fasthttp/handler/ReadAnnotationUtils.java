package com.mani.fasthttp.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
public class ReadAnnotationUtils {

    private static final PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    private static final SimpleMetadataReaderFactory register = new SimpleMetadataReaderFactory();
    private static final StandardEnvironment environment = new StandardEnvironment();


    /**
     * 根据包路径,获取Class的资源路径
     *
     * @param packagePath
     * @return
     */
    public static String getResourcePath(String packagePath) {
        if (StringUtils.isEmpty(packagePath)) return "";
        String resourcePath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                + ClassUtils.convertClassNameToResourcePath(environment.resolveRequiredPlaceholders(packagePath))
                + '/' + "**/*.class";
        return resourcePath;
    }

    /**
     * 获取指定路径下的类
     *
     * @param pkgPath
     * @param annoClazz
     * @return
     */
    public static Set<Class<?>> getClazzFromAnnotation(String pkgPath, Class<? extends Annotation> annoClazz) {
        //获取spring的包路径
        String pathPackage = getResourcePath(pkgPath);

        Set<Class<?>> paths = new HashSet<>();
        Resource[] resources;
        try {
            //加载路径
            resources = resolver.getResources(pathPackage);
        } catch (IOException e) {
            //异常处理
            return new HashSet<>();
        }
        for (int i = 0; i < resources.length; i++) {
            Resource resource = resources[i];

            MetadataReader metadataReader = null;
            try {
                //读取资源
                metadataReader = register.getMetadataReader(resource);
            } catch (IOException e) {
                continue;
            }
            //读取资源的注解配置
            AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
            //判断是否包含注解
            if (!annotationMetadata.hasAnnotation(annoClazz.getName())) continue;
            //类信息
            ClassMetadata classMetadata = metadataReader.getClassMetadata();
            //类全名
            String className = classMetadata.getClassName();
            log.info("lrms_http package scan to Class：[{}]", className);
            try {
                //加载类
                /*ClassLoader classLoader = ClassLoader.getSystemClassLoader();
                Class<?> clazz = classLoader.loadClass(className);*/
                Class<?> clazz = Class.forName(className);
                paths.add(clazz);
            } catch (ClassNotFoundException e) {
                log.error("lrms_http ClassLoad Error:[{}]，message:[{}]", className, e.getCause());
            }
        }
        return paths;
    }


    public static String getPackageByAnnotation(ApplicationContext ctx, Class<? extends Annotation> annotation) {
        Map<String, Object> springBootStartBean = ctx.getBeansWithAnnotation(annotation);
        String key = springBootStartBean.keySet().stream().findFirst().orElse(null);
        Object obj = springBootStartBean.get(key);
        return obj.getClass().getPackage().getName();
    }
}