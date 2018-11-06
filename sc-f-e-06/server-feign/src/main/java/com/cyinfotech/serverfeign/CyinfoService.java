package com.cyinfotech.serverfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("eureka-client")
@Service
public interface CyinfoService {

    @RequestMapping(method = RequestMethod.GET, value = "/hi")
    String hi (@RequestParam(value = "name") String name);
}
