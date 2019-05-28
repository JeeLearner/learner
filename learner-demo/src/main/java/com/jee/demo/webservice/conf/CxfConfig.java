package com.jee.demo.webservice.conf;

import com.jee.demo.webservice.server.DemoService;
import com.jee.demo.webservice.server.DemoServiceImpl;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.EndpointException;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;

/**
 * @Description:
 * @Auther: lyd
 * @Date: 2019/5/24
 * @Version:v1.0
 */
@Configuration
public class CxfConfig {

    /**
     * 这里的方法名不要写dispatcherServlet，会报错
     * @return
     */
    @Bean
    public ServletRegistrationBean wsServlet() {
        return new ServletRegistrationBean(new CXFServlet(),"/demo/*");
    }

    /**
     * 注册一个dispatcherServlet,解决增加ws之后http接口访问不了问题
     */
    @Bean
    public ServletRegistrationBean restServlet(){
        //注解扫描上下文
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        //base package
        applicationContext.scan("com.jee");
        //通过构造函数指定dispatcherServlet的上下文
        DispatcherServlet rest_dispatcherServlet = new DispatcherServlet(applicationContext);

        //用ServletRegistrationBean包装servlet
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(rest_dispatcherServlet);
        registrationBean.setLoadOnStartup(1);
        //指定urlmapping
        registrationBean.addUrlMappings("/*");
        //指定name，如果不指定默认为dispatcherServlet
        registrationBean.setName("rest");
        return registrationBean;
    }




    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }

    @Autowired
    DemoService demoService;

    @Bean
    public Endpoint endpoint() throws EndpointException {
        EndpointImpl endpoint = new EndpointImpl(springBus(), demoService);
        //endpoint.getInInterceptors().add(new AuthInterceptor());//添加校验拦截器
        endpoint.publish("/api");
        return endpoint;
    }

}

