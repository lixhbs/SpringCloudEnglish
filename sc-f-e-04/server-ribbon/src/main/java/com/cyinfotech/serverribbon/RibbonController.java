package com.cyinfotech.serverribbon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 煮酒泛舟.
 * @title
 * @program sc-f-e-03
 * @description
 * @createtime 2018-10-28 09:44
 */
@RestController
public class RibbonController {

    @Autowired
    HelloCyInfo helloCyInfo;

    @RequestMapping("/")
    public String hiCYIT ( @RequestParam String name) {

        return helloCyInfo.helloCYIT(name);
    }
}
