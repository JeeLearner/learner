package com.jee.service.sys;

import com.jee.service.sys.entity.SysUser;
import com.jee.service.sys.service.SysRoleService;
import com.jee.service.sys.service.SysUserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

/**
 * @Description:
 * @Auther: lyd
 * @Date: 2019/5/20
 * @Version:v1.0
 */
@SpringBootTest
public class UserServiceTest {

    @Autowired
    SysUserService sysUserService;

    @MockBean
    private SysRoleService sysRoleService;

    @Test
    public void testService(){
        int userId = 10;
        int expectedCredit = 100;

        given(this.sysRoleService.userListNum(anyInt()))
           .willReturn(expectedCredit);

        int i = sysRoleService.userListNum(userId);
        System.out.println(i);

        assertEquals(1, i);

    }
}

