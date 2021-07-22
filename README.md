# 项目说明

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
