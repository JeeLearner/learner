package com.jee.demo.webservice.server;

import com.jee.demo.api.User;
import org.springframework.stereotype.Service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * @Description:
 * @Auther: lyd
 * @Date: 2019/5/24
 * @Version:v1.0
 */
@WebService(name = "demoService",  //暴露服务名称
        targetNamespace = "http://server.webservice.demo.jee.com"  // 命名空间,一般是接口的包名倒序
)
public interface DemoService {

    @WebMethod
    public User getUser(@WebParam(name = "userId")int userId);

    @WebMethod
    public String getUser1(@WebParam(name = "userId")int userId);
}

