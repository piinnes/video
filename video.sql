/*
 Navicat Premium Data Transfer

 Source Server         : Demo
 Source Server Type    : MySQL
 Source Server Version : 80015
 Source Host           : localhost:3306
 Source Schema         : video

 Target Server Type    : MySQL
 Target Server Version : 80015
 File Encoding         : 65001

 Date: 05/12/2019 22:42:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for collect
-- ----------------------------
DROP TABLE IF EXISTS `collect`;
CREATE TABLE `collect`  (
  `id` int(16) NOT NULL AUTO_INCREMENT COMMENT '采集表id',
  `name` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '采集名称',
  `desc_` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '采集说明',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '采集创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of collect
-- ----------------------------
INSERT INTO `collect` VALUES (8, '8', '8', '2019-12-03 17:55:25');
INSERT INTO `collect` VALUES (9, '9', '9', '2019-12-03 17:55:31');
INSERT INTO `collect` VALUES (10, '10', '10', '2019-12-03 17:55:34');
INSERT INTO `collect` VALUES (11, '11', '11', '2019-12-03 20:06:13');
INSERT INTO `collect` VALUES (15, '12', '12', '2019-12-03 12:19:57');
INSERT INTO `collect` VALUES (16, '13', '13', '2019-12-05 13:21:43');
INSERT INTO `collect` VALUES (17, '14', '14', '2019-12-05 13:21:47');
INSERT INTO `collect` VALUES (18, '15', '15', '2019-12-05 13:21:52');

-- ----------------------------
-- Table structure for image
-- ----------------------------
DROP TABLE IF EXISTS `image`;
CREATE TABLE `image`  (
  `id` int(32) NOT NULL AUTO_INCREMENT COMMENT '图片id',
  `url` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片地址',
  `rab_id` int(32) NULL DEFAULT NULL COMMENT '垃圾类别',
  `state` int(32) NULL DEFAULT NULL COMMENT '0未审核  1已审核  -1图片失效',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '拍摄时间',
  `collect_id` int(16) NULL DEFAULT NULL COMMENT '采集表id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of image
-- ----------------------------
INSERT INTO `image` VALUES (1, '/image/20191127233334.png', 7, 0, '2019-12-03 09:48:19', 8);
INSERT INTO `image` VALUES (2, '/image/20191127233331.png', 3, 0, '2019-12-03 09:48:19', 8);
INSERT INTO `image` VALUES (3, '/image/20191127233328.png', 5, 0, '2019-12-03 09:48:19', 8);
INSERT INTO `image` VALUES (4, '/image/20191127233324.png', 1, 0, '2019-12-03 09:48:19', 8);
INSERT INTO `image` VALUES (5, '/image/20191127233321.png', 3, 0, '2019-12-03 09:48:19', 8);
INSERT INTO `image` VALUES (13, '/image/20191127151035.png', 5, 0, '2019-12-03 09:48:19', 8);
INSERT INTO `image` VALUES (14, '/image/20191127151057.png', 4, 0, '2019-12-03 09:48:19', 8);
INSERT INTO `image` VALUES (17, '/image/20191127173402.png', 5, 0, '2019-12-03 09:48:19', 8);
INSERT INTO `image` VALUES (20, '/image/20191127203907.png', 3, 0, '2019-12-03 09:48:19', 8);
INSERT INTO `image` VALUES (21, '/image/20191127203941.png', 4, 0, '2019-12-03 09:48:19', 9);
INSERT INTO `image` VALUES (22, '/image/20191127203955.png', 1, 0, '2019-12-03 09:48:19', 9);
INSERT INTO `image` VALUES (23, '/image/20191127204008.png', 7, 0, '2019-12-03 09:48:19', 9);
INSERT INTO `image` VALUES (24, '/image/20191127204026.png', 1, 0, '2019-12-03 09:48:19', 8);
INSERT INTO `image` VALUES (25, '/image/20191127204037.png', 9, 0, '2019-12-03 09:48:19', 10);
INSERT INTO `image` VALUES (26, '/image/20191127204052.png', 9, 0, '2019-12-03 09:48:19', 10);
INSERT INTO `image` VALUES (32, '/image/20191129191025.png', 5, 0, '2019-12-03 09:48:19', 8);
INSERT INTO `image` VALUES (33, '/image/20191203174819.png', 6, 0, '2019-12-03 09:48:19', NULL);
INSERT INTO `image` VALUES (34, '/image/20191203204606.png', 8, 0, '2019-12-03 12:46:07', 11);
INSERT INTO `image` VALUES (35, '/image/15/20191204203505.png', 8, 0, '2019-12-04 12:35:05', 15);
INSERT INTO `image` VALUES (36, '/image/15/20191204203558.png', 2, 0, '2019-12-04 12:35:58', 15);
INSERT INTO `image` VALUES (37, '/image/15/20191204203602.png', 1, 0, '2019-12-04 12:36:02', 15);
INSERT INTO `image` VALUES (38, '/image/15/20191204203607.png', 4, 0, '2019-12-04 12:36:08', 15);

-- ----------------------------
-- Table structure for rabbish
-- ----------------------------
DROP TABLE IF EXISTS `rabbish`;
CREATE TABLE `rabbish`  (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rabbish
-- ----------------------------
INSERT INTO `rabbish` VALUES (1, '厨余垃圾');
INSERT INTO `rabbish` VALUES (2, '可回收-塑料');
INSERT INTO `rabbish` VALUES (3, '可回收-纸类');
INSERT INTO `rabbish` VALUES (4, '可回收-金属');
INSERT INTO `rabbish` VALUES (5, '可回收-其它');
INSERT INTO `rabbish` VALUES (6, '其它-塑料');
INSERT INTO `rabbish` VALUES (7, '其它-纸类');
INSERT INTO `rabbish` VALUES (8, '其它-其它');
INSERT INTO `rabbish` VALUES (9, '有害垃圾');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', 'admin');
INSERT INTO `user` VALUES (2, 'user', 'user');

SET FOREIGN_KEY_CHECKS = 1;
