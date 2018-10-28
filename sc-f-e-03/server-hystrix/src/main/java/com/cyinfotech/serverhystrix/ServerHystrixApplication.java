package com.cyinfotech.serverhystrix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@SpringBootApplication
@EnableCircuitBreaker
@EnableDiscoveryClient
@RestController
public class ServerHystrixApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ServerHystrixApplication.class).web(true).run(args);
    }

    @Autowired
    StoreIntegration storeIntegration;

    @Value("${server.port}")
    String port;

    @RequestMapping("/")
    public Object getHi (@RequestParam String name){

        return "HI - " + name + "， port:" + port + ", By server-hystrix.";
    }
}
