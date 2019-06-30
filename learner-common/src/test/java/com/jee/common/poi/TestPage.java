package com.jee.common.poi;

import com.jee.common.utils.Page;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Auther: lyd
 * @Date: 2019/6/6
 * @Version:v1.0
 */
@SpringBootTest
public class TestPage {
    //页码
    public static final String PAGE = "currentPage";
    //条数
    public static final String PAGE_SIZE = "pageSize";
    //开始
    public static final String PAGE_START = "start";
    @Test
    public void testPage(){
        int pageSize = 10;
        int currentPage = 2;
        int totalCount = 32;
        Page<Person> page = new Page(currentPage,pageSize, totalCount, getList());
        System.out.println(page.toString());

    }

    public List<Person> getList(){
        List<Person> list = new ArrayList<>();
        for (int i=0; i<32; i++){
            Person p = new Person();
            p.setId(Long.parseLong(String.valueOf(i)));
            p.setName("p" + i);
            list.add(p);
        }
        return list;
    }
}

