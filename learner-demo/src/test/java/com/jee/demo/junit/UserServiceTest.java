package com.jee.demo.junit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.*;

/**
 * @Description: 。
 * @Auther: lyd
 * @Date: 2019/5/20
 * @Version:v1.0
 */

/**
 * sysUserService依赖creditSystemService，但是creditSystemService未开发完毕
 * Mockito默认单元测试完毕后事务总是回滚，若不希望回滚，可在方法放使用@Rollback(false)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    SysUserService sysUserService;

    @MockBean
    private CreditSystemService creditSystemService;

    @Test
    public void testService(){
        int userId = 10;
        int expectedCredit = 100;

        given(this.creditSystemService.getUserCredit(anyInt()))
           .willReturn(expectedCredit);

        int i = sysUserService.getCredit(userId);
        System.out.println(i);

        assertEquals(expectedCredit, i);

    }
}

