package com.cyinfotech.serverribbon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author 煮酒泛舟.
 * @title
 * @program sc-f-e-03
 * @description
 * @createtime 2018-10-28 09:38
 */
@Service
public class HelloCyInfo {

    @Autowired
    RestTemplate restTemplate;

    public String helloCYIT(String name) {
        return restTemplate.getForObject("http://server-hystrix/?name="+name,String.class);
    }
}
