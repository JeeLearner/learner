package com.jee.common.utils;

import com.google.common.base.Strings;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Auther: lyd
 * @Date: 2019/6/6
 * @Version:v1.0
 */
public class Page<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int DEFAULT_PAGE_SIZE = 10;
    private final int DEFAULT_CURRENT_PAGE = 1;

    //总记录数
    private int totalCount;
    //每页记录数
    private int pageSize = DEFAULT_PAGE_SIZE;
    //总页数
    private int totalPage;
    //当前页数
    private int currentPage = DEFAULT_CURRENT_PAGE;
    //列表数据
    private List<T> list;

    public Page() {
    }

    public Page(int currentPage, int pageSize, int totalCount,List<T> list) {
        this.currentPage = currentPage;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.list = list;
        /**
         * 页数为计算出的结果值，不能进行人工set
         */
        this.totalPage = (int)Math.ceil((double)totalCount/pageSize);
    }










    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}

