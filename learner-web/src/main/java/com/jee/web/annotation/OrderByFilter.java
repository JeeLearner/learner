package com.jee.web.annotation;

import java.lang.annotation.*;

/**
 * @Description: mybatis排序处理
 * @Version:v1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OrderByFilter {

}
