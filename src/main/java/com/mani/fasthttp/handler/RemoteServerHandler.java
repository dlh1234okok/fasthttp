package com.mani.fasthttp.handler;

import com.mani.fasthttp.ext.RemoteServerException;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Dulihong
 * @since 2020-12-10
 */
@Slf4j
public class RemoteServerHandler {

    private static Map<String, String> servers;

    private static List<String> scanPackages;


    public static void initServer(Map<String, String> servers) {
        if (null == servers) {
            log.error("未配置远程调用服务：[lrms.http.server]");
            servers = new LinkedHashMap<>();
        }
        RemoteServerHandler.servers = servers;
    }

    public static void initScanPackages(String scanPackages) {
        RemoteServerHandler.scanPackages = Arrays.asList(scanPackages.split(","));
    }

    public static String getServerPath(String server) {
        String serverPath = servers.get(server);
        if (null == serverPath) {
            throw new RemoteServerException("服务[" + server + "]未找到");
        }
        return serverPath;
    }

    public static List<String> getScanPackages() {
        return scanPackages;
    }

}
