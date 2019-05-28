package com.jee.demo.restlocal;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jee.demo.restremote.JeeAuthenticationScheme;
import org.apache.http.HttpHost;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

/**
 * @Description:
 * @Auther: lyd
 * @Date: 2019/5/21
 * @Version:v1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RestLocalTest {

    @Autowired
    RestTemplate restTemplate;

    private final String  IP = "10.178.46.205";
    private final int PORT = 8086;
    private final String USERNAME = "admin";
    private final String PASSWORD = "admin";
    private final String URL = "http://"+IP+":"+PORT+"/fos/modules/basics/getServerTime";
    private final String TOKEN = "dc7f2e16b6076dd90bb6ecc93f7ff5fd";

    @Test
    public void test(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.set("token", TOKEN);
        HttpEntity httpEntity = new HttpEntity(String.class, headers);

        ResponseEntity<String> exchange = restTemplate.exchange(URL, HttpMethod.GET, httpEntity, String.class);
        String body = exchange.getBody();
        //{"code":0,"msg":"success","data":"2019-05-21 11:27:01"}
        System.out.println(body);
    }
}

