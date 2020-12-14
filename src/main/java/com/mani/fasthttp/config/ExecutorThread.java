package com.mani.fasthttp.config;

import cn.hutool.core.thread.ExecutorBuilder;

import java.util.concurrent.ExecutorService;

/**
 * @author Dulihong
 * @since 2020-12-10
 */
public class ExecutorThread {


    public static class Executor {
        public static final ExecutorService INSTANCE = ExecutorBuilder.create()
                .setCorePoolSize(5)
                .setMaxPoolSize(10)
                .build();

    }

    public static ExecutorService getInstance() {
        return Executor.INSTANCE;
    }


}
