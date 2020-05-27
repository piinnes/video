/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80015
 Source Host           : localhost:3306
 Source Schema         : information_gather

 Target Server Type    : MySQL
 Target Server Version : 80015
 File Encoding         : 65001

 Date: 21/05/2020 20:40:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for collect
-- ----------------------------
DROP TABLE IF EXISTS `collect`;
CREATE TABLE `collect`  (
  `id` int(16) NOT NULL AUTO_INCREMENT COMMENT '采集表id',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '采集名称',
  `desc_` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '采集说明',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '采集创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 94 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for collect_img
-- ----------------------------
DROP TABLE IF EXISTS `collect_img`;
CREATE TABLE `collect_img`  (
  `id` int(32) NOT NULL AUTO_INCREMENT COMMENT '采集图片id',
  `url` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '采集图片url',
  `state` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '0未审核  1已审核  -1图片失效',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '拍摄时间',
  `collect_id` int(16) NULL DEFAULT NULL COMMENT '所属采集id',
  `rubbish_id` int(16) NULL DEFAULT NULL COMMENT '所属垃圾类别id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `collect_id`(`collect_id`) USING BTREE,
  INDEX `rabbish_id`(`rubbish_id`) USING BTREE,
  CONSTRAINT `collect_img_ibfk_1` FOREIGN KEY (`collect_id`) REFERENCES `collect` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `collect_img_ibfk_2` FOREIGN KEY (`rubbish_id`) REFERENCES `rubbish` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 142 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rubbish
-- ----------------------------
DROP TABLE IF EXISTS `rubbish`;
CREATE TABLE `rubbish`  (
  `id` int(32) NOT NULL AUTO_INCREMENT COMMENT '垃圾类别id',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '垃圾类别名字',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rubbish
-- ----------------------------
INSERT INTO `rubbish` VALUES (1, '厨余垃圾');
INSERT INTO `rubbish` VALUES (2, '可回收-塑料');
INSERT INTO `rubbish` VALUES (3, '可回收-纸类');
INSERT INTO `rubbish` VALUES (4, '可回收-金属');
INSERT INTO `rubbish` VALUES (5, '可回收-其它');
INSERT INTO `rubbish` VALUES (6, '其它-塑料');
INSERT INTO `rubbish` VALUES (7, '其它-纸类');
INSERT INTO `rubbish` VALUES (8, '其它-其它');

-- ----------------------------
-- Table structure for rubbish_img
-- ----------------------------
DROP TABLE IF EXISTS `rubbish_img`;
CREATE TABLE `rubbish_img`  (
  `id` int(64) NOT NULL AUTO_INCREMENT COMMENT '垃圾类别图片id',
  `url` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '垃圾类别图片url',
  `state` int(32) NULL DEFAULT 0 COMMENT '0未审核  1已审核  -1图片失效',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '拍摄时间',
  `rubbish_id` int(32) NULL DEFAULT NULL COMMENT '所属垃圾类别id',
  `collect_id` int(32) NULL DEFAULT NULL COMMENT '所属采集id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `rabbish_id`(`rubbish_id`) USING BTREE,
  INDEX `collect_id`(`collect_id`) USING BTREE,
  CONSTRAINT `rubbish_img_ibfk_1` FOREIGN KEY (`rubbish_id`) REFERENCES `rubbish` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `rubbish_img_ibfk_2` FOREIGN KEY (`collect_id`) REFERENCES `collect` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 493 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '21232f297a57a5a743894a0e4a801fc3');

SET FOREIGN_KEY_CHECKS = 1;
