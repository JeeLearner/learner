package com.jee.service.sys.controller;

import com.jee.common.result.ResultInfo;
import com.jee.service.sys.entity.SysUser;
import com.jee.service.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description:
 * @Auther: lyd
 * @Date: 2019/5/20
 * @Version:v1.0
 */
@RestController
@RequestMapping(value = "/sys/user")
public class SysUserController {

    @Autowired
    SysUserService sysUserService;

    @GetMapping(value = "/list")
    public ResultInfo list(){
        List<SysUser> list = sysUserService.list();
        return ResultInfo.success(list);
    }
}

