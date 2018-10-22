# 【参照官方英文文档学Spring Cloud】第一篇 Service Discovery | 服务发现 - Eureka

> 从来没有写过类似的教程，也只能写一步算一步了。
> 想通过这个文档来摸索通过阅读英文文档学习最新的技术，说实话让我去背单词还不如SHA了我，而且我也没有那毅力。在这个过程中如果有啥问题希望大家一起沟通，有好的建议也希望能告诉我，感激不尽！！！
> 

看到Spring Cloud 的文档里面是先讲的是`Spring Cloud Config` ，为了方便，或者说参考其他大佬的教程，我也会把Config放到后面写。

# word & phrase
- include       确实是包含的意思，但是我觉得翻译（根据语境去翻译）成使用是不是更舒服些
- setting up    配置
- provides      提供
- meta-data     元数据
- such as       例如
- Note          注意


# Spring Cloud Netflix
  
[Netflix](https://en.wikipedia.org/wiki/Netflix) 是一家媒体提供商，应该很厉害！

官网文档中先列出的是Eureka客户端，我尝试先写`Eureka Client` 发现会提示以下错误，应该是在请求 `Eureka server`的时候报错， 因为我们根本没有创建。所以我先看`Eureka server`   
```
NFO 25402 --- [main] com.netflix.discovery.DiscoveryClient    : Getting all instance registry info from the eureka server
ERROR 25402 --- [main] c.n.d.s.t.d.RedirectingEurekaHttpClient  : Request execution error
```

### 说明   
**官方说明 Finchley的构建和工作环境应该是 `Spring Boot 2.0.x`, 预计不会在`Spring Boot 1.5.x` 中使用。**
> Finchley builds and works with Spring Boot 2.0.x, and is not expected to work with Spring Boot 1.5.x.       

**Spring Cloud 需要使用Spring Boot，如果不会创建Spring Boot项目的看这里《[Spring Boot | 使用Spring Initializr快速创建](../Spring-Boot-SI)》！**

### 创建项目
   
- 新建好Spring Boot后，删除src文件
![](http://paz1myrij.bkt.clouddn.com/20181016165813.png)

- 在`pom.xml` 中添加 `Spring Cloud` 配置
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.cyinfotech</groupId>
    <artifactId>sc-f-e-01</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>sc-f-e-01</name>
    <description>Demo project for Spring Boot</description>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.0.5.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
    
         <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <!-- Spring Cloud -->
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>Finchley.SR1</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>

```

## Service Discovery: Eureka Server | 服务发现：Eureka服务端

### How to Include Eureka Server | 项目中如何包含 `Eureka Server`

- 右键项目 > New > Module 新建 `eureka-server` 子工程
![](http://paz1myrij.bkt.clouddn.com/20181016170056.png)
![](http://paz1myrij.bkt.clouddn.com/20181016171250.png)

- 修改父工程`pom.xml`文件, 添加子工程。
```xml
    <modules>
        <module>eureka-server</module>
    </modules>
```

> To include Eureka Server in your project, use the starter with a group ID of `org.springframework.cloud` and an `artifact ID of spring-cloud-starter-netflix-eureka-server`. See the [Spring Cloud Project page](http://projects.spring.io/spring-cloud/) for details on setting up your build system with the current Spring Cloud Release Train.   

在项目中使用`Eureka Server`，需要配置 `pom.xml`, 根据你当前的Spring Cloud 版本在[Spring Cloud Project page](http://projects.spring.io/spring-cloud/)中可以查看详细说明。

`eureka-server` 的 `pom.xml` 
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.cyinfotech</groupId>
    <artifactId>eureka-server</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>eureka-server</name>
    <description>Demo project for Spring Boot</description>

    <parent>
        <groupId>com.cyinfotech</groupId>
        <artifactId>sc-f-e-01</artifactId>
        <version>0.0.1-SNAPSHOT</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>

    <dependencies>
        
        <!--Eureka-Server-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>
        
    </dependencies>

</project>

```

### How to Run a Eureka Server | 如何启动 `Eureka Server`

在启动类添加 注解 `@EnableEurekaServer`
- Modify `EurekaServerApplication` 

```java
package com.cyinfotech.eurekaserver;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaServerApplication.class).web(true).run(args);
    }
}

```

### Standalone Mode | 单机模式

- Add `application.yml` 

```
server:
  port: 8761 # 端口

eureka:
  instance:
    hostname: localhost # 主机
  client:
    registerWithEureka: false #是否注册自己
    fetchRegistry: false #是否注册自己
    serviceUrl:
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/ # 服务地址
```

- Start 
![](http://paz1myrij.bkt.clouddn.com/20181018110257.png)

- Open `http://127.0.0.1:8761/` in your browser
![](http://paz1myrij.bkt.clouddn.com/20181018110319.png)

Instances currently registered with Eureka 是空的， 因为有没有服务注册!

## Service Discovery: Eureka Clients | 服务发现：Eureka客户端

Eureka客户端可以理解为服务注册实际项目中需要曝光的服务。

### How to Include Eureka Client | 如何使用Eureka客户端

- 右键项目 > New > Module 新建 `eureka-client` 子工程，和添加 server 一样

- 修改父工程`pom.xml`文件, 添加子工程。   

```xml
    <module>eureka-client</module>
```

> To include the Eureka Client in your project, use the starter with a group ID of `org.springframework.cloud` and an artifact ID of `spring-cloud-starter-netflix-eureka-client`. See the [Spring Cloud Project page](http://projects.spring.io/spring-cloud/)  for details on setting up your build system with the current Spring Cloud Release Train.

要使用`Eureka Client` 就要添加配置文件，完整`pom.xml`配置文件如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.cyinfotech</groupId>
    <artifactId>eureka-client</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>eureka-client</name>
    <description>Demo project for Spring Boot</description>

    <parent>
        <groupId>com.cyinfotech</groupId>
        <artifactId>sc-f-e-01</artifactId>
        <version>0.0.1-SNAPSHOT</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>

        <!--Eureka-Client-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>

    </dependencies>

</project>

```
### Registering with Eureka | 注册 Eureka
>When a client registers with Eureka, it provides meta-data about itself — such as host, port, health indicator URL, home page, and other details. Eureka receives heartbeat messages from each instance belonging to a service. If the heartbeat fails over a configurable timetable, the instance is normally removed from the registry.

当注册Eureka时，它提供一些包括自己的源数据，例如：主机、端口、监听(心跳)地址、主页和其它详细信息。Eureka 通过每个实例的服务接受心跳消息。 如果心跳在配置的时间失败结束，那这个实例通常会删除注册。

修改启动`Eureka Client` 启动类
```java
package com.cyinfotech.eurekaclient;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class EurekaClientApplication {

    @RequestMapping("/")
    public String home() {
        return "Hello world";
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaClientApplication.class).web(true).run(args);
    }
}

```

>Note that the preceding example shows a normal Spring Boot application. By having `spring-cloud-starter-netflix-eureka-client` on the classpath, your application automatically registers with the Eureka Server. Configuration is required to locate the Eureka server, as shown in the following example:

注意前面的正常的 `Spring boot` 程序， 你的应用程序中添加`spring-cloud-starter-netflix-eureka-client`后将自动注册到`Eureka Server`, 如下所示：

```yaml
server:
  port: 8781
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
```

启动步骤：
    1.EurekaServerApplication
    2.EurekaClientApplication

http://localhost:8761/       
![](http://paz1myrij.bkt.clouddn.com/20181022174105.png)
    
http://192.168.5.165:8781/    
![](http://paz1myrij.bkt.clouddn.com/20181022174136.png)

以上 Eureka 的服务端和客户端（注册与发现）就讲完了，我也是第一次这么认真看官方文档，其实看下来发现也不难，其他人的文档里面也无非就直译了官方文档，但是每个人的理解不一样，所以还是推荐自己去看原版文档， 没有想象的那么难。


------------
博客地址：[https://blog.aprcode.com/](https://blog.aprcode.com/)
教程源码Github地址：[https://github.com/lixhubei/SpringCloudEnglish](https://github.com/lixhubei/SpringCloudEnglish)
教程源码Gitee地址：[https://gitee.com/Lixhbs/SpringCloudEnglish](https://gitee.com/Lixhbs/SpringCloudEnglish)









