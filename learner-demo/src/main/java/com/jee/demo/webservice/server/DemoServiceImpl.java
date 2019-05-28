package com.jee.demo.webservice.server;

import com.alibaba.fastjson.JSON;
import com.jee.demo.api.User;
import org.apache.cxf.interceptor.InInterceptors;
import org.springframework.stereotype.Service;

import javax.jnlp.UnavailableServiceException;
import javax.jws.WebService;

/**
 * @Description:
 * @Auther: lyd
 * @Date: 2019/5/24
 * @Version:v1.0
 */
@Service
//@InInterceptors(interceptors={"com.jee.demo.webservice.conf.AuthInterceptor"})
@WebService(name = "demoService",  //与接口中指定的name一致
        targetNamespace = "http://server.webservice.demo.jee.com",  // 与接口中的命名空间一致,一般是接口的包名倒序
        endpointInterface = "com.jee.demo.webservice.server.DemoService" // 接口地址
)
public class DemoServiceImpl implements DemoService {


    @Override
    public User getUser(int userId) {
        return new User(userId, "jee");
    }

    @Override
    public String getUser1(int userId) {
        return "jee:"+1;
    }
}

