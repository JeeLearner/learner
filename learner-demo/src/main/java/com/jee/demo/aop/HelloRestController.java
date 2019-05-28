package com.jee.demo.aop;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Auther: lyd
 * @Date: 2019/5/19
 * @Version:v1.0
 */
@RestController
public class HelloRestController {

    @GetMapping(value = "/hello2")
    public String hello(){
        return "hello, you";
    }

    @GetMapping(path = "/hello/{id}")
    public String hello(@PathVariable("id") Long id){
        return "hello:" + id;
    }
}

