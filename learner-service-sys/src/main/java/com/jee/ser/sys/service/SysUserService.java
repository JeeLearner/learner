package com.jee.ser.sys.service;

import com.jee.ser.sys.dao.SysUserDao;
import com.jee.ser.sys.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Auther: lyd
 * @Date: 2019/5/20
 * @Version:v1.0
 */
@Service
public class SysUserService{

    @Autowired
    SysUserDao sysUserDao;

    public List<SysUser> list() {
        return sysUserDao.list();
    }
}

