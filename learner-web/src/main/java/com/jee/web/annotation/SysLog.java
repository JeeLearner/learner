package com.jee.web.annotation;

import java.lang.annotation.*;

/**
 * @Description: 系统日志注解
 * @Version:v1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

	String value() default "";
}