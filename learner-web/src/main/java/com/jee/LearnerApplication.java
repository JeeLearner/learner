package com.jee;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * @Description:
 * @Auther: lyd
 * @Date: 2019/5/15
 * @Version:v1.0
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@MapperScan(basePackages = {"com.jee.ser.*.dao"})
public class LearnerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearnerApplication.class);
    }
}

