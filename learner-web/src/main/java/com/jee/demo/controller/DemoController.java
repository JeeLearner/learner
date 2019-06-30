package com.jee.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jee.web.annotation.OrderByFilter;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Description:
 * @Auther: lyd
 * @Date: 2019/6/6
 * @Version:v1.0
 */
@RestController
@RequestMapping(value = "/demo")
public class DemoController {

    @RequestMapping(value = "/orderby")
    @OrderByFilter
    public void testOrderBy(@RequestBody Map<String, Object> params) {
        params.forEach((k, v) -> {
            System.out.println(k + "===" + v);
        });
    }
}

