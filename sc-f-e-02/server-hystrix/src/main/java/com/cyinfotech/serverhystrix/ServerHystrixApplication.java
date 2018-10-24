package com.cyinfotech.serverhystrix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@SpringBootApplication
@EnableCircuitBreaker
@RestController
public class ServerHystrixApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ServerHystrixApplication.class).web(true).run(args);
    }

    @Autowired
    StoreIntegration storeIntegration;

    @RequestMapping("/")
    public Object getHi (Map<String, Object> parameters){

        return storeIntegration.getStores(parameters);
    }
}
