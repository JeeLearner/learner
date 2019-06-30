package com.jee;

import com.alibaba.fastjson.JSONObject;
import com.jee.demo.controller.DemoController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @Description:
 * @Auther: lyd
 * @Date: 2019/6/6
 * @Version:v1.0
 */
@RunWith(SpringRunner.class)
@WebMvcTest({DemoController.class})
public class DemoTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testOrderby() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("field", "id");
        params.put("sortKey", "ASC");
        String content = JSONObject.toJSONString(params);
        mvc.perform(post("/demo/orderby")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        /*mvc.perform(post("/demo/orderby",params))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();*/
    }
}

