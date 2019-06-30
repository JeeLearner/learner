package com.jee.ser.sys;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description:
 * @Auther: lyd
 * @Version:v1.0
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.jee.service.sys.dao"})
public class SysApplication {

    public static void main(String[] args) {
        SpringApplication.run(SysApplication.class);
    }
}

