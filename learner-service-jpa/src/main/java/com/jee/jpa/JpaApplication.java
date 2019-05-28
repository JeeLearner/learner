package com.jee.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @Description:
 * @Auther: lyd
 * @Date: 2019/5/19
 * @Version:v1.0
 */
@SpringBootApplication
public class JpaApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(JpaApplication.class);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(JpaApplication.class);
    }
}

