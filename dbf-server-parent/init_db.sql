/*
 Navicat Premium Data Transfer

 Source Server         : 124.222.105.58
 Source Server Type    : MySQL
 Source Server Version : 50729 (5.7.29-log)
 Source Host           : 124.222.105.58:3306
 Source Schema         : dbf_db

 Target Server Type    : MySQL
 Target Server Version : 50729 (5.7.29-log)
 File Encoding         : 65001

 Date: 17/07/2024 23:30:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for task_dependens
-- ----------------------------
DROP TABLE IF EXISTS `task_dependens`;
CREATE TABLE `task_dependens`  (
                                   `id` bigint(17) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                   `app_id` bigint(17) NULL DEFAULT NULL COMMENT '应用Id',
                                   `group_id` bigint(17) NULL DEFAULT NULL COMMENT '分组Id',
                                   `dependen_app_id` bigint(17) NULL DEFAULT NULL COMMENT '分组Id',
                                   `dependen_group_id` bigint(17) NULL DEFAULT NULL COMMENT '分组Id',
                                   `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                   `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                   PRIMARY KEY (`id`) USING BTREE,
                                   UNIQUE INDEX `unique_index`(`app_id`, `group_id`, `dependen_app_id`, `dependen_group_id`) USING BTREE COMMENT '应用分组唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for task_group
-- ----------------------------
DROP TABLE IF EXISTS `task_group`;
CREATE TABLE `task_group`  (
                               `id` bigint(17) NOT NULL AUTO_INCREMENT COMMENT '主键',
                               `app_id` bigint(17) NULL DEFAULT NULL COMMENT '应用Id',
                               `group_id` bigint(17) NULL DEFAULT NULL COMMENT '分组Id',
                               `group_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '分组名称',
                               `group_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '分组描述',
                               `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                               `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                               PRIMARY KEY (`id`) USING BTREE,
                               UNIQUE INDEX `unique_index`(`app_id`, `group_id`) USING BTREE COMMENT '应用分组唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;









