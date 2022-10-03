/*
 Navicat Premium Data Transfer

 Source Server         : Example
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : localhost:3306
 Source Schema         : hdfs

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 14/08/2021 23:48:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('oys', '123456');
INSERT INTO `user` VALUES ('xiaoming', '123');
INSERT INTO `user` VALUES ('eee', 'eee');
INSERT INTO `user` VALUES ('ttt', 'ttt');
INSERT INTO `user` VALUES ('zz', 'zz');

SET FOREIGN_KEY_CHECKS = 1;
