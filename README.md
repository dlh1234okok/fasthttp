# 使用方法：

## 1.启用

​    @EnableHttpClient

## 2.配置

    配置远程调用接口目录 fast.http.scanPackages: com.mani.xx1,com.mani.xx2
    配置远程调用服务 fast.http.server:
                                server1: http://localhost:8080
                                server2: http://localhost:8081
## 3.使用

​	Service注解

​    GetMapping/PostMapping/PutMapping/DeleteMapping 注解

![image-20201218144119563](https://s3.ax1x.com/2020/12/18/rJhTaj.jpg)

直接注入即可调用