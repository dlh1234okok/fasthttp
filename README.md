使用方法：
1.启用
    @EnableHttpClient
2.配置
    配置远程调用接口目录 fast.http.scanPackages: com.mani.xx1,com.mani.xx2
    配置远程调用服务 fast.http.server:
                                server1: http://localhost:8080
                                server2: http://localhost:8081

3.使用
    GetMapping/PostMapping/PutMapping/DeleteMapping 注解