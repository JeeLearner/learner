package com.jee.service.sys.dao;

import com.jee.service.sys.entity.SysUser;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 开启二级缓存
 */
@CacheNamespace
@Mapper
public interface SysUserDao {

    @Select("select * from sys_user")
    List<SysUser> list();
}
