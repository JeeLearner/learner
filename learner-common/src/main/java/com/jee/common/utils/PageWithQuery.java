package com.jee.common.utils;

import org.apache.commons.collections.map.UnmodifiableMap;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description:
 * @Auther: lyd
 * @Date: 2019/6/6
 * @Version:v1.0
 */
public class PageWithQuery<T> extends LinkedHashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    private Page<T> page;
    /**
     * 当前页码
     */
    private int currentPage = 1;
    /**
     * 每页条数
     */
    private int pageSize = 10;

    public PageWithQuery(Map<String, Object> params) {

    }


}

