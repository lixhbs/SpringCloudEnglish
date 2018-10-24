package com.cyinfotech.serverhystrix;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author 煮酒泛舟.
 * @title Component
 * @program sc-f-e-02
 * @description
 * @createtime 2018-10-24 09:28
 */

@Component
public class StoreIntegration {

    @HystrixCommand(fallbackMethod = "defaultStores")
    public Object getStores(Map<String, Object> parameters) {

        //do stuff that might fail
        throw new RuntimeException("call service fail.");
    }

    public Object defaultStores(Map<String, Object> parameters) {

        /* something useful */
        return "Sorry, error!!! fallback defaultStores service！";
    }
}
