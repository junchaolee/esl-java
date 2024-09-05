/*
Navicat MySQL Data Transfer

Source Server         : 192.168.0.130
Source Server Version : 80032
Source Host           : 192.168.0.130:3306
Source Database       : fs1

Target Server Type    : MYSQL
Target Server Version : 80032
File Encoding         : 65001

Date: 2024-09-05 18:49:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `userid` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `password` varchar(30) NOT NULL DEFAULT '',
  `codec` varchar(255) DEFAULT NULL,
  `createtime` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for call_record
-- ----------------------------
DROP TABLE IF EXISTS `call_record`;
CREATE TABLE `call_record` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `call_uuid` varchar(36) NOT NULL COMMENT '关联ID，同主叫方UUID',
  `caller_id_name` varchar(56) DEFAULT '' COMMENT '主叫方昵称',
  `caller_id_number` varchar(56) DEFAULT NULL COMMENT '主叫号码',
  `destination_number` varchar(56) DEFAULT NULL COMMENT '被叫号码',
  `start_stamp` bigint DEFAULT NULL COMMENT '呼叫发起的日期/时间',
  `answer_stamp` bigint DEFAULT NULL COMMENT '实际应答呼叫远端的日期/时间 如果未接听电话，则为空字符串',
  `end_stamp` bigint DEFAULT NULL COMMENT '呼叫终止的日期/时间',
  `uduration` bigint DEFAULT NULL COMMENT '总呼叫持续时间（以微秒为单位）',
  `billsec` int DEFAULT NULL COMMENT '可计费的通话时长（秒）可计费时间不包括在远端接听电话之前在“早期媒体”中花费的通话时间',
  `hangup_cause` varchar(255) DEFAULT NULL COMMENT '挂断原因',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Table structure for call_sound
-- ----------------------------
DROP TABLE IF EXISTS `call_sound`;
CREATE TABLE `call_sound` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `call_uuid` varchar(36) NOT NULL,
  `file_path` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Table structure for conference
-- ----------------------------
DROP TABLE IF EXISTS `conference`;
CREATE TABLE `conference` (
  `conf_name` varchar(20) DEFAULT NULL,
  `member_id` varchar(20) DEFAULT NULL,
  `create_time` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `is_video` varchar(10) DEFAULT NULL,
  `user_id` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for extensions
-- ----------------------------
DROP TABLE IF EXISTS `extensions`;
CREATE TABLE `extensions` (
  `userid` varchar(5) NOT NULL DEFAULT '',
  `password` varchar(30) NOT NULL DEFAULT '',
  `displayname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `vmpasswd` varchar(10) DEFAULT NULL,
  `accountcode` varchar(10) DEFAULT NULL,
  `outbound_caller_id_name` varchar(14) DEFAULT NULL,
  `outbound_caller_id_number` varchar(14) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for parameters_info
-- ----------------------------
DROP TABLE IF EXISTS `parameters_info`;
CREATE TABLE `parameters_info` (
  `parameter_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `parameter_value` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `create_time` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for router
-- ----------------------------
DROP TABLE IF EXISTS `router`;
CREATE TABLE `router` (
  `router_name` varchar(20) DEFAULT NULL,
  `caller_regex` varchar(20) DEFAULT NULL,
  `called_regex` varchar(20) DEFAULT NULL,
  `gw_id` int DEFAULT NULL COMMENT '绑定网关的ID',
  `create_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for sip_gateway
-- ----------------------------
DROP TABLE IF EXISTS `sip_gateway`;
CREATE TABLE `sip_gateway` (
  `id` int NOT NULL AUTO_INCREMENT,
  `gw_name` varchar(20) DEFAULT NULL COMMENT '网关名称',
  `gw_i_p` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '网关地址（含端口）',
  `gw_type` varchar(20) DEFAULT NULL COMMENT '网关类型：中继、三汇',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
