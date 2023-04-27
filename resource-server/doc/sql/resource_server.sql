/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80030 (8.0.30)
 Source Host           : localhost:3306
 Source Schema         : resource_server

 Target Server Type    : MySQL
 Target Server Version : 80030 (8.0.30)
 File Encoding         : 65001

 Date: 27/04/2023 13:52:04
*/

CREATE DATABASE resource_server;
USE resource_server;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '加密后的密码',
  `gender` tinyint NULL DEFAULT NULL COMMENT '性别。1：男，0：女',
  `hobby` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '爱好',
  `enabled` tinyint(1) NOT NULL COMMENT '是否启用。1：启用，0：禁用',
  PRIMARY KEY (`username`) USING BTREE,
  UNIQUE INDEX `uk_username`(`username` ASC) USING BTREE COMMENT '用户名唯一'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户信息（定义业务系统需要的用户信息）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('chen', '{bcrypt}$2a$10$LXtBmt.3GXSpnTMxodF04OIO6XGIZX8qa30zvP0nnu1TerCRFDwFO', 1, '吹牛，唠嗑', 1);
INSERT INTO `t_user` VALUES ('michael', '{bcrypt}$2a$10$YIva8lbxKynkdloBNbv2FeZlpNXk4/As3NMlLe3LM83kOK9av5OBq', 1, '拉皮条', 1);

SET FOREIGN_KEY_CHECKS = 1;
