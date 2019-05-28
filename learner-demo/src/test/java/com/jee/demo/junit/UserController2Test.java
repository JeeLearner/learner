package com.jee.demo.junit;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import javax.servlet.http.Cookie;

import static org.mockito.BDDMockito.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 2.比较MVC的返回结果
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UserController2.class)
//@WebMvcTest(value = {})
public class UserController2Test {

    @Autowired
    private MockMvc mvc;

    @MockBean
    SysUserService userService;

    /**
     *  这里有个坑：
     * java.lang.IllegalArgumentException: Unknown return value type: java.lang.Integer
     * 需要在Controller中添加@ResponseBody
     * @throws Exception
     */
    @Test
    public void testMvcForResponse() throws Exception {
        int userId = 10;
        String username = "jee";
        int expectedCredit = 100;

        //mvc调用
        mvc.perform(get("/junit/user2/get/{id}", userId))
                .andExpect(status().isOk())  //期望成功调用，即HTTP Status为200
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))  //期望返回内容格式为application/json
                .andExpect(jsonPath("$..name").value("jee"));  //检查返回内容

        //对ModeAndView进行校验
        mvc.perform(get("/junit/user2/modelView"))
                .andExpect(status().isOk())  //期望成功调用，即HTTP Status为200
                .andExpect(view().name("/success.btl"));

        //比较Model
        mvc.perform(get("/junit/user2/model"))
                .andExpect(status().isOk())
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("person"))
                .andExpect(model().attribute("person", "jee"));

        //比较forward或 redirect
        mvc.perform(get("/junit/user2/forward"))
                .andExpect(forwardedUrl(("index.html")));
        mvc.perform(get("/junit/user2/redirect"))
                .andExpect(redirectedUrl(("index.html")));

        //比较返回内容
        mvc.perform(get("/junit/user2/content"))
                .andExpect(content().string("hello world"))
                //返回内容是XML，并且与xmlContent一样
                //.andExpect(content().xml(xmlContent))
                //返回内容是json，且与jsonContent一样
                //.andExpect(content().json(jsonContent))
                //.andExpect(content().bytes(bytes))
        ;

    }


    /**
     * JSON比较
     * jsonPath说明：
     *      api: https://github.com/json-path/JsonPath
     *      $  代表json的根节点
     *      $.store.book[*].auther  所有book的auther
     *      $..auther  所有auther
     *      $.store.*  store下的所有节点
     *      $..book[2]  第三个book的节点
     *      $..book.length()  返回book的个数
     *      $..book[0,1]  第一个和第二个book节点
     *      $.store.book[?(@.price<10)]  所有price小于10的节点，[?()] 包含了表达式，@指示的是当前节点
     */
    /**
        {
            "store": {
                "book": [
                    {
                        "title": "标题1",
                        "auther": "jee1"
                    },{
                        "title": "标题2",
                        "auther": "jee2"
                    }
                ]
            }
        }
     */
    @Test
    public void testMvcForJson() throws Exception {
        int userId = 10;
        String path = "$.success";

        mvc.perform(get("/junit/user2/get/{id}", userId))
                .andExpect(status().isOk())  //期望成功调用，即HTTP Status为200
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))  //期望返回内容格式为application/json
                .andExpect(jsonPath(path).value(true));  //检查返回内容
    }
}

