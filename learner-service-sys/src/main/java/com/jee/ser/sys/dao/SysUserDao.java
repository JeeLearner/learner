package com.jee.ser.sys.dao;

import com.jee.ser.sys.entity.SysUser;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 开启二级缓存
 */
@CacheNamespace
@Repository
public interface SysUserDao {

    @Select("select * from sys_user")
    List<SysUser> list();
}
