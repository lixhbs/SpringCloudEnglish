package com.cyinfotech.serverfeign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 煮酒泛舟.
 * @title controller
 * @program sc-f-e-06
 * @description controller
 * @createtime 2018-11-06 10:38
 */
@RestController
public class CyinfoController {

    @Autowired
    CyinfoService cyinfoService;

    @RequestMapping(value = "/hi")
    public String hi (@RequestParam(value = "name") String name) {
        return cyinfoService.hi(name);
    }
}
