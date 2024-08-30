/*
Navicat MySQL Data Transfer

Source Server         : 192.168.0.130
Source Server Version : 80032
Source Host           : 192.168.0.130:3306
Source Database       : fs1

Target Server Type    : MYSQL
Target Server Version : 80032
File Encoding         : 65001

Date: 2024-08-30 18:02:48
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
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES ('977988', '456789', 'PCMA,PCMU,OPUS,G722,VP8,H264', '2024-08-30 17:14:19');
INSERT INTO `account` VALUES ('988988', '456789', 'PCMA,PCMU,OPUS,G722,VP8,H264', '2024-08-30 17:21:01');
INSERT INTO `account` VALUES ('721721', '1234', 'PCMA,PCMU,OPUS,G722,VP8,H264', '2024-08-30 17:33:42');
INSERT INTO `account` VALUES ('722722', '1234', 'PCMA,PCMU,OPUS,G722,VP8,H264', '2024-08-30 17:33:47');
INSERT INTO `account` VALUES ('321321', '1234', 'PCMA,PCMU,OPUS,G722,VP8,H264', '2024-08-30 17:33:54');
INSERT INTO `account` VALUES ('231226', '1234', 'PCMA,PCMU,OPUS,G722,VP8,H264', '2024-08-30 17:34:31');

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
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of call_record
-- ----------------------------
INSERT INTO `call_record` VALUES ('51', 'd884a847-9952-4fbd-acea-e3d48ae182bb', '721721', '130-721', '231226', '1724895823275553', '1724895823295524', '1724895836895846', null, '23', 'NORMAL_CLEARING');
INSERT INTO `call_record` VALUES ('52', '3770c8cb-7394-4608-835e-8bbe8f373361', '721721', '130-721', '231226', '1724898971575521', '1724898971595524', '1724899043375692', null, '23', 'NORMAL_CLEARING');
INSERT INTO `call_record` VALUES ('53', 'ef4d6d67-07a3-414d-bdf0-9d438cf20957', '322322', '322322', '231227', '1724915270535521', '1724915270555531', '1724915305795525', null, '23', 'NORMAL_CLEARING');
INSERT INTO `call_record` VALUES ('54', 'b1a9edf1-ae79-410a-8ff3-e435eaab754b', '721721', '130-721', '321321', '1724915305735521', '1724915305755524', '1724915332275523', null, '23', 'NORMAL_CLEARING');
INSERT INTO `call_record` VALUES ('55', 'bcc81c8a-cc68-42dd-bb38-ffbe34c22451', '721721', '130-721', '321321', '1724916752555550', '1724916757735523', '1724916765975821', null, '23', 'NORMAL_CLEARING');
INSERT INTO `call_record` VALUES ('56', '703e7380-96da-44c3-9143-ef7fdca5f35e', '721721', '130-721', '321321', '1724917097455528', '1724917097615731', '1724917121115822', null, '23', 'NORMAL_CLEARING');
INSERT INTO `call_record` VALUES ('57', '127a7d67-d628-4a41-9b96-4a3c7a282b6d', '721721', '130-721', '321321', '1724919078655691', '1724919078835785', '1724919085035537', null, '23', 'NORMAL_CLEARING');
INSERT INTO `call_record` VALUES ('58', 'd18ed324-f24e-4b1a-9dfb-4a1cc9a829f7', '721721', '130-721', '18698552278', '1724919211915552', '1724919215395523', '1724919236875538', null, '23', 'NORMAL_CLEARING');
INSERT INTO `call_record` VALUES ('59', '2a3b0f2f-5a08-4df4-bac4-1055d1b48fef', '721721', '130-721', '18698552278', '1724919305795839', '1724919309295526', '1724919501715551', null, '23', 'NORMAL_CLEARING');
INSERT INTO `call_record` VALUES ('60', 'fc92c562-bfe9-4ae9-8471-103007145341', '721721', '130-721', '321321', '1724990187650424', '1724990188630413', '1724990198130396', null, '23', 'NORMAL_CLEARING');
INSERT INTO `call_record` VALUES ('61', '1b3a7aeb-da7e-4d3f-b4ba-d4ac0887ba06', '721721', '130-721', '321321', '1724990238270429', '1724990238430398', '1724990253330395', null, '23', 'NORMAL_CLEARING');
INSERT INTO `call_record` VALUES ('62', 'e1b1a36f-c025-45ef-bb1d-180f421a5dd5', '721721', '130-721', '321321', '1724990257870428', '1724990262030427', '1724990267330395', null, '23', 'NORMAL_CLEARING');
INSERT INTO `call_record` VALUES ('63', '1b1c3e35-8b08-442b-a93c-f0bf201fbdde', '721721', '130-721', '321321', '1724990303310411', '1724990303470397', '1724990316250399', null, '23', 'NORMAL_CLEARING');
INSERT INTO `call_record` VALUES ('64', 'c836de78-c5ef-4065-a93e-645ec73b4c7e', '721721', '130-721', '18698552278', '1724990330610411', '1724990334090402', '1724990356610395', null, '23', 'NORMAL_CLEARING');
INSERT INTO `call_record` VALUES ('65', 'ac314419-b0cf-46e1-ba55-93ef729f102f', '721721', '130-721', '18698552278', '1724990372210402', '1724990375690402', '1724990392750412', null, '23', 'NORMAL_CLEARING');
INSERT INTO `call_record` VALUES ('66', '12e17d19-c608-41bb-8ede-fe4424fe80b3', '721721', '130-721', '18698552278', '1724990407910414', '1724990411390397', '1724990438790396', null, '23', 'NORMAL_CLEARING');
INSERT INTO `call_record` VALUES ('67', '81ab5f2e-84bb-49f9-a2e7-9a5ee9d26229', '721721', '130-721', '18698552278', '1724990445930402', '1724990449470396', '1724990489370398', null, '23', 'NORMAL_CLEARING');
INSERT INTO `call_record` VALUES ('68', 'ba2aa6b1-6a32-4f08-a1e8-28b8cb512565', '721721', '130-721', '18698552278', '1724990588530426', '1724990592010422', '1724990604210402', null, '23', 'NORMAL_CLEARING');
INSERT INTO `call_record` VALUES ('69', '8b6d92af-307b-4344-8639-7e593702fe90', '721721', '130-721', '18698552278', '1724994435650411', '1724994439130399', '1724994454710399', null, '23', 'NORMAL_CLEARING');
INSERT INTO `call_record` VALUES ('70', '0d484c33-5221-40a9-b524-ee6c9cb15130', '721721', '130-721', '18698552278', '1724994462870401', '1724994466370398', '1724994531350397', null, '23', 'NORMAL_CLEARING');

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
-- Records of call_sound
-- ----------------------------
INSERT INTO `call_sound` VALUES ('2', '6ac17c46-a9aa-424e-879e-d73bcf036c6a', '/tmp/record-1002-1005-2024-08-22-14-05-27.wav');
INSERT INTO `call_sound` VALUES ('3', 'c2829ef9-52e5-459e-9c34-c817d63bb16b', '/tmp/record-1003-1005-2024-08-22-14-06-10.wav');
INSERT INTO `call_sound` VALUES ('4', '6ec7a4b7-a344-4530-bcb5-57fd9132e128', '/tmp/record-1003-1005-2024-08-22-14-08-13.wav');
INSERT INTO `call_sound` VALUES ('5', '62275735-24ff-4150-9c3d-bc89b82a67fc', '/tmp/record-1002-1005-2024-08-22-14-15-42.wav');
INSERT INTO `call_sound` VALUES ('6', '8d840e93-b84b-43b8-8e27-b8634d7703e3', '/tmp/record-1002-1005-2024-08-22-16-35-46.wav');

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
-- Records of extensions
-- ----------------------------
INSERT INTO `extensions` VALUES ('1000', '12345', 'GiovanniMaruzzelli', '123456', '1000', 'OpenTelecom.IT', '+393472665618');
INSERT INTO `extensions` VALUES ('1001', '12345', 'Sara Sissi', '123456', '1001', 'OpenAdassi.IR', '+12125551212');
INSERT INTO `extensions` VALUES ('1002', '12345', 'Francesca Francesca', '123456', '1002', 'OpenSantuzza', '+4433344422');
INSERT INTO `extensions` VALUES ('1003', '12345', 'Tommaso Stella', '123456', '1003', 'OpenBoat.LUV', '+9188877755');

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
-- Records of router
-- ----------------------------

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

-- ----------------------------
-- Records of sip_gateway
-- ----------------------------
INSERT INTO `sip_gateway` VALUES ('1', 'gwfxo8', '192.168.0.240:5077', 'trunk');
