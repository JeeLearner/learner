CREATE DATABASE `learner` DEFAULT CHARACTER SET utf8;

USE learner;

DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(100) DEFAULT NULL,
  `email` VARCHAR(100) DEFAULT NULL,
  `mobile` VARCHAR(20) DEFAULT NULL,
  `password` VARCHAR(100) DEFAULT NULL,
  `salt` VARCHAR(20) DEFAULT NULL,
  `status` TINYINT(1) DEFAULT NULL COMMENT '0：禁用   1：正常',
  `create_date` DATETIME NOT NULL COMMENT '创建时间',
  `deleted` TINYINT(1) DEFAULT NULL COMMENT '0：已删除   1：正常',
  `remarks` VARCHAR(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_sys_user_username` (`username`) USING BTREE,
  UNIQUE KEY `unique_sys_user_email` (`email`) USING BTREE
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `name` VARCHAR(100) DEFAULT NULL COMMENT '角色名称',
  `role` VARCHAR(100) DEFAULT NULL COMMENT '角色标识',
  `description` VARCHAR(200) DEFAULT NULL COMMENT '角色描述',
  `create_date` DATETIME NOT NULL COMMENT '创建时间',
  `status` TINYINT(1) DEFAULT NULL COMMENT '0：禁用   1：正常',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_sys_role_role` (`role`) USING BTREE
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '资源id',
  `name` VARCHAR(50) DEFAULT NULL COMMENT '菜单名称',
  `url` VARCHAR(200) DEFAULT NULL COMMENT '菜单URL',
  `perms` VARCHAR(500) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `parent_id` BIGINT(20) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `type` TINYINT(1) DEFAULT NULL COMMENT '类型 0:目录 1:菜单 2:按钮',
  `icon` VARCHAR(50) DEFAULT NULL COMMENT '菜单图标',
  `weight` INT(11) DEFAULT NULL COMMENT '权重',
  `status` TINYINT(1) DEFAULT NULL COMMENT '0：禁用   1：正常',
  PRIMARY KEY (`id`),
  KEY `idx_sys_menu_name` (`name`) USING BTREE,
  KEY `idx_sys_menu_user` (`url`) USING BTREE,
  KEY `idx_sys_menu_parent_id` (`parent_id`) USING BTREE
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` BIGINT(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`),
  KEY `idx_sys_user_role` (`user_id`) USING BTREE
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户与角色对应关系';

DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `role_id` BIGINT(20) DEFAULT NULL COMMENT '角色ID',
  `menu_id` BIGINT(20) DEFAULT NULL COMMENT '资源ID',
  PRIMARY KEY (`id`),
  KEY `idx_sys_role_menu` (`role_id`) USING BTREE
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='角色与资源对应关系';

-- 系统用户Token
DROP TABLE IF EXISTS `sys_user_token`;
CREATE TABLE `sys_user_token` (
  `user_id` BIGINT(20) NOT NULL,
  `token` VARCHAR(100) NOT NULL COMMENT 'token',
  `system` VARCHAR(100) NOT NULL COMMENT '所属系统',
  `expire_time` DATETIME DEFAULT NULL COMMENT '过期时间',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  `remark` VARCHAR(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `token` (`token`),
  KEY `idx_sys_user_token_system` (`system`) USING BTREE
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='系统用户Token';

-- 系统日志
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(50) COMMENT '用户名',
  `operation` VARCHAR(50) COMMENT '操作',
  `method` VARCHAR(200) COMMENT '请求方法',
  `params` VARCHAR(5000) COMMENT '请求参数',
  `result`TINYINT(1) DEFAULT NULL COMMENT '结果 0:成功 1:失败',
  `time` BIGINT NOT NULL COMMENT '执行时长(毫秒)',
  `ip` VARCHAR(64) COMMENT 'IP地址',
  `record_date` DATETIME COMMENT '记录时间',
  PRIMARY KEY (`id`),
  KEY `idx_sys_log_username` (`username`) USING BTREE
) ENGINE=`InnoDB` DEFAULT CHARACTER SET utf8 COMMENT='系统日志';
