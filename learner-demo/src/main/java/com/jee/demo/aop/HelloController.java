package com.jee.demo.aop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Description:
 * @Auther: lyd
 * @Date: 2019/5/19
 * @Version:v1.0
 */
@Controller
public class HelloController {

    @GetMapping(value = "/hello")
    public String hello(){
        return "hello, you";
    }
}

