-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        10.1.13-MariaDB - mariadb.org binary distribution
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  9.1.0.4867
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出 soft 的数据库结构
DROP DATABASE IF EXISTS `soft`;
CREATE DATABASE IF NOT EXISTS `soft` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `soft`;


-- 导出  表 soft.sys_group 结构
DROP TABLE IF EXISTS `sys_group`;
CREATE TABLE IF NOT EXISTS `sys_group` (
  `group_id` int(11) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(50) NOT NULL DEFAULT '0',
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- 正在导出表  soft.sys_group 的数据：~0 rows (大约)
DELETE FROM `sys_group`;
/*!40000 ALTER TABLE `sys_group` DISABLE KEYS */;
INSERT INTO `sys_group` (`group_id`, `group_name`) VALUES
	(1, '超级管理员');
/*!40000 ALTER TABLE `sys_group` ENABLE KEYS */;


-- 导出  表 soft.sys_group_menu 结构
DROP TABLE IF EXISTS `sys_group_menu`;
CREATE TABLE IF NOT EXISTS `sys_group_menu` (
  `group_id` int(11) NOT NULL,
  `menu_id` varchar(50) NOT NULL,
  PRIMARY KEY (`group_id`,`menu_id`),
  KEY `FK_sys_group_menu_sys_menu` (`menu_id`),
  CONSTRAINT `FK_sys_group_menu_sys_group` FOREIGN KEY (`group_id`) REFERENCES `sys_group` (`group_id`),
  CONSTRAINT `FK_sys_group_menu_sys_menu` FOREIGN KEY (`menu_id`) REFERENCES `sys_menu` (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限表';

-- 正在导出表  soft.sys_group_menu 的数据：~4 rows (大约)
DELETE FROM `sys_group_menu`;
/*!40000 ALTER TABLE `sys_group_menu` DISABLE KEYS */;
INSERT INTO `sys_group_menu` (`group_id`, `menu_id`) VALUES
	(1, 'home'),
	(1, 'system'),
	(1, 'sys_group'),
	(1, 'sys_permiss');
/*!40000 ALTER TABLE `sys_group_menu` ENABLE KEYS */;


-- 导出  表 soft.sys_menu 结构
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE IF NOT EXISTS `sys_menu` (
  `menu_id` varchar(50) NOT NULL,
  `menu_name` varchar(50) NOT NULL COMMENT '菜单名',
  `menu_icon` varchar(50) DEFAULT NULL COMMENT '菜单图标，仅对一级菜单有效',
  `menu_url` varchar(255) DEFAULT NULL COMMENT '菜单链接',
  `parent_id` varchar(50) NOT NULL COMMENT '上级菜单',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- 正在导出表  soft.sys_menu 的数据：~4 rows (大约)
DELETE FROM `sys_menu`;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_icon`, `menu_url`, `parent_id`) VALUES
	('home', '首页', 'fa-home', '/index.html', ''),
	('system', '系统设置', 'fa-cogs', NULL, ''),
	('sys_group', '角色设置', NULL, '0', 'system'),
	('sys_permiss', '权限管理', NULL, '0', 'system');
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;


-- 导出  表 soft.sys_user 结构
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE IF NOT EXISTS `sys_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) NOT NULL DEFAULT '0' COMMENT '账号',
  `password` varchar(50) NOT NULL DEFAULT '0' COMMENT '密码',
  `real_name` varchar(50) NOT NULL DEFAULT '0' COMMENT '真实姓名',
  `mobile` varchar(50) NOT NULL DEFAULT '0' COMMENT '电话号码',
  `group_id` int(11) NOT NULL DEFAULT '0' COMMENT '角色',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态：1-启用、0-停用',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_name` (`user_name`),
  KEY `FK__sys_group` (`group_id`),
  CONSTRAINT `FK__sys_group` FOREIGN KEY (`group_id`) REFERENCES `sys_group` (`group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- 正在导出表  soft.sys_user 的数据：~0 rows (大约)
DELETE FROM `sys_user`;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` (`user_id`, `user_name`, `password`, `real_name`, `mobile`, `group_id`, `status`) VALUES
	(1, 'admin', 'admin', '超级管理员', '15645155210', 1, 1);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
