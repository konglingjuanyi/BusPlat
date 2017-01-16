/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50621
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2017-01-16 19:02:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for zy_app_info
-- ----------------------------
DROP TABLE IF EXISTS `zy_app_info`;
CREATE TABLE `zy_app_info` (
  `id` bigint(20) NOT NULL,
  `name` bigint(20) NOT NULL COMMENT '名称',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `description` varchar(1000) DEFAULT NULL COMMENT 'app描述',
  `found_time` datetime DEFAULT NULL COMMENT '立项时间',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_status` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `un` (`name`,`remark`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zy_app_suggest_feedback
-- ----------------------------
DROP TABLE IF EXISTS `zy_app_suggest_feedback`;
CREATE TABLE `zy_app_suggest_feedback` (
  `id` bigint(20) NOT NULL,
  `title` varchar(200) DEFAULT NULL,
  `content` varchar(2000) DEFAULT NULL,
  `pics` varchar(1000) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `user_phone` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_status` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zy_app_version
-- ----------------------------
DROP TABLE IF EXISTS `zy_app_version`;
CREATE TABLE `zy_app_version` (
  `id` bigint(20) NOT NULL,
  `app_id` bigint(20) DEFAULT NULL COMMENT 'app标识',
  `title` varchar(200) DEFAULT NULL COMMENT '标题',
  `content` varchar(2000) DEFAULT NULL,
  `version` varchar(20) DEFAULT NULL,
  `client_os` varchar(100) DEFAULT NULL,
  `client_os_version` varchar(100) DEFAULT NULL,
  `force_upgrade` int(11) DEFAULT '0',
  `is_latest` int(11) DEFAULT '0' COMMENT '是否为最新版本（0不是，1是）',
  `issue_uid` bigint(20) DEFAULT NULL COMMENT '版本发行人',
  `develop_uid` bigint(20) DEFAULT NULL COMMENT '开发人',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_status` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='app版本号信息';
