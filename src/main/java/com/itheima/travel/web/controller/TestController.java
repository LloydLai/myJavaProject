package com.itheima.travel.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author LloydLai
 * @version 1.0
 * @Description: TODO
 * @date Created in 2019/4/17 23:03
 * @Modified By: TODO
 */
@Controller
@RequestMapping("/user")
public class TestController {

    @RequestMapping("/add")
    public String test() {
        System.out.println("我找到你了！~");
        return "test";
    }
}
