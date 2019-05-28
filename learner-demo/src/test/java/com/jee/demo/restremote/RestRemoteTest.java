package com.jee.demo.restremote;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpHost;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description:
 * @Auther: lyd
 * @Date: 2019/5/21
 * @Version:v1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RestRemoteTest {

    @Autowired
    JeeAuthenticationRestTemplate jeeAuthenticationRestTemplate;

    private final String  IP = "10.168.110.4";
    private final int PORT = 80;
    private final String USERNAME = "admin";
    private final String PASSWORD = "admin";


    @Test
    public void test(){
        HttpHost host = new HttpHost(IP, PORT, "http");
        jeeAuthenticationRestTemplate.setCredentials(host, JeeAuthenticationScheme.BASIC_AUTHENTICATION, USERNAME, PASSWORD);
        String str = jeeAuthenticationRestTemplate.getForObject("http://"+IP+":"+PORT+"/imcrs/uam/acmUser/acmDetailList?start=0&size=10&orderBy=id&desc=false&total=false", String.class);
        JSONObject jsonObject = JSON.parseObject(str);
        System.out.println(jsonObject);
    }
}

