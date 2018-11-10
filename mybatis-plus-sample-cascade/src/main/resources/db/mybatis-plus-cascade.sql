/*
 Navicat Premium Data Transfer

 Source Server         : mysql_local
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : localhost:3306
 Source Schema         : mybatis-plus-cascade

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : 65001

 Date: 12/10/2018 13:38:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'admin', '2018-10-10 12:43:24');
INSERT INTO `role` VALUES (2, 'sa', '2018-10-10 16:05:44');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role_id` int(11) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'joker', 'sadasd', 1, '2018-10-10 14:59:43');
INSERT INTO `user` VALUES (2, 'JOKER', 'sadasd', NULL, '2018-10-10 14:59:43');
INSERT INTO `user` VALUES (3, 'das', 'sadasd', 2, '2018-10-10 14:59:43');
INSERT INTO `user` VALUES (4, 'asxX', 'sadasd', 2, '2018-10-10 14:59:43');
INSERT INTO `user` VALUES (5, 'dsa', 'sadasd', 2, '2018-10-10 14:59:43');
INSERT INTO `user` VALUES (6, 'das', 'sadasd', NULL, '2018-10-10 14:59:43');
INSERT INTO `user` VALUES (7, 'ss', 'sadasd', NULL, '2018-10-10 14:59:43');
INSERT INTO `user` VALUES (8, 'jokeras', 'sadasd', NULL, '2018-10-10 14:59:43');
INSERT INTO `user` VALUES (9, 'dsad', 'sadasd', NULL, '2018-10-10 14:59:43');
INSERT INTO `user` VALUES (10, 'dasda', 'sadasd', NULL, '2018-10-10 14:59:43');
INSERT INTO `user` VALUES (11, 'wangerxiao-plus', 'Adssad', 2, '2018-10-12 13:16:31');

SET FOREIGN_KEY_CHECKS = 1;
