# 项目说明

spring配置文件中配置: 在pac4j中, 每一个client相当于一种认证协议, 比如需要CAS认证登录, 则要配置对应的CasClient, 用于完成验证授权。按需定义所有的Client以及默认的client还有关于如何区分回掉的哪个client应该取某个参数的配置.

1. 配置CasConfiguration(loginUrl,prefixUrl): loginUrl为完整的cas登录地址,比如client项目的<https://cas.com/login?service=https://client.com>, prefixUrl则为cas路径前缀, 根据cas的版本号拼接请求地址,用于验证sts是否正确并且返回登录成功后的信息。
2. 配置CasClient(CasConfiguration,callbackUrl): 定义Client, 在这里面可以定义你所有的Client以及默认的client还有关于如何区分回掉的哪个client应该取某个参数的配置.
3. 定义Config,在config里面还有关于权限方面的配置以及session存储的一些配置,由于这部分我交给shiro去管理,所以只传入了clients即可
4. 以上四个都是pac4j的配置,接下来配置一个由buji-pac4j提供用于和shiro结合的filter:CallbackFilter,直接传入config即可
5. 定义好CallbackFilter以后,在ShiroFilterFactoryBean中注册好filters,并且配置好filterChainDefinitions

## 文件说明

### java/

filter/Redirect2CasLoginFilter.java

测试用例

client/MyCasClient.java

继承自CasClient的自定义客户端

handler/ShiroCasLogoutHandler.java

单点登出时对shiro的一些操作

rest/Controller.java

请求控制器

util/

包里的是单点登出相关的类；

### resources/

- log4j.properties
日志管理文件

- url.properties
配置了项目中用到的各种url

- spring-comm.xml
配置了shiro集成pac4j的配置

- spring-mvc.xml
springmvc的相关配置

### wabapp/

index.jsp

受保护的页面，请求访问前需要先认证

web.xml

配置了springmvc和shiroFilter

## TEST

部署CAS Server与两个pac4jtest项目

WebServer Tomcat
