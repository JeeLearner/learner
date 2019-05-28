package com.jee.demo.junit;

import com.alibaba.fastjson.JSON;
import com.jee.demo.api.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import javax.servlet.http.Cookie;

import java.io.File;
import java.io.FileInputStream;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 1.MVC请求模拟
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
//@WebMvcTest(value = {})
public class UserControllerTest {

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
    public void testMvcForRequest() throws Exception {
        int userId = 10;
        String username = "jee";
        int expectedCredit = 100;

        //模拟userService
        given(this.userService.getCredit(anyInt()))
                .willReturn(expectedCredit);

        //mvc调用
        mvc.perform(get("/junit/user/get/{id}",userId))
                .andExpect(content().string(String.valueOf(expectedCredit)));
        mvc.perform(post("/junit/user/post/{id}",userId))
                .andExpect(content().string(String.valueOf(expectedCredit)));

        //模拟提交message参数
        mvc.perform(get("/junit/user/get/{id}/{name}",userId, username)
                    .param("message", "内容"))
                .andExpect(content().string(String.valueOf(expectedCredit)));

        //模拟一个checkbox提交
        mvc.perform(get("/junit/user/get/{id}/{name}/2",userId, username)
                    .param("jobs", "IT","Teacher")
                    .param("message", "内容"))
                .andExpect(content().string(String.valueOf(expectedCredit)));
        //直接LinkedMultiValueMap使用构造参数
        LinkedMultiValueMap params = new LinkedMultiValueMap();
        params.add("message","内容");
        params.add("jobs","IT");
        params.add("jobs","Teacher");
        mvc.perform(get("/junit/user/get/{id}/{name}/2", userId, username)
                    .params(params))
                .andExpect(content().string(String.valueOf(expectedCredit)));

        //模拟session和cookie
        mvc.perform(get("/junit/user/session")
                    .sessionAttr("sessionName", "xxxxxxxx"))
                .andExpect(content().string(String.valueOf(expectedCredit)));
        mvc.perform(get("/junit/user/cookie")
                    .cookie(new Cookie("cookieName", "yyyyyyyyyyyy")))
                .andExpect(content().string(String.valueOf(expectedCredit)));

        //设置http Body内容，比如提交的json
        String json = JSON.toJSONString(new User(1,"jee"));
        mvc.perform(get("/junit/user/json")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(json))
                .andExpect(content().string(String.valueOf(expectedCredit)));

        //设置http header
        mvc.perform(get("/junit/user/header")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .accept(MediaType.APPLICATION_JSON)  //期望返回内容
                    .header("header1", "value1"))
                .andExpect(content().string(String.valueOf(expectedCredit)));
    }

    /**
     * 测试文件上传
     * @throws Exception
     */
    @Test
    public void fileTest1() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("fileupload", "test.xlsx","application/ms-excel", new FileInputStream(new File("E:/test.xlsx")));
        mvc.perform(multipart("/junit/user/upload")
                .file(mockMultipartFile))
                .andExpect(status().isOk());
    }


}

