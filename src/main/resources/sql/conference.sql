/*
Navicat MySQL Data Transfer

Source Server         : 192.168.0.130
Source Server Version : 80032
Source Host           : 192.168.0.130:3306
Source Database       : fs1

Target Server Type    : MYSQL
Target Server Version : 80032
File Encoding         : 65001

Date: 2024-09-02 18:14:20
*/

SET FOREIGN_KEY_CHECKS=0;

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
