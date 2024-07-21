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

 Date: 21/07/2024 17:42:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for task_depend
-- ----------------------------
DROP TABLE IF EXISTS `task_depend`;
CREATE TABLE `task_depend`  (
                                `id` bigint(17) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                `depend_id` bigint(17) NULL DEFAULT NULL COMMENT '依赖Id',
                                `app_id` bigint(17) NULL DEFAULT NULL COMMENT '应用Id',
                                `group_id` bigint(17) NULL DEFAULT NULL COMMENT '分组Id',
                                `depend_app_id` bigint(17) NULL DEFAULT NULL COMMENT '分组Id',
                                `depend_group_id` bigint(17) NULL DEFAULT NULL COMMENT '分组Id',
                                `depend_type` tinyint(2) NULL DEFAULT NULL COMMENT '依赖类型',
                                `depend_batch` int(10) NULL DEFAULT NULL COMMENT '依赖批次',
                                `depend_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '依赖描述',
                                `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                PRIMARY KEY (`id`) USING BTREE,
                                UNIQUE INDEX `unique_index`(`app_id`, `group_id`, `depend_app_id`, `depend_group_id`) USING BTREE COMMENT '应用分组唯一索引',
                                INDEX `unique_depend_id_index`(`depend_id`) USING BTREE COMMENT '依赖id唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '任务依赖表' ROW_FORMAT = Dynamic;

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
                               UNIQUE INDEX `unique_group_id_index`(`group_id`) USING BTREE COMMENT '分组唯一索引',
                               INDEX `app_group_index`(`app_id`, `group_id`) USING BTREE COMMENT '应用分组唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '任务分组表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for task_info
-- ----------------------------
DROP TABLE IF EXISTS `task_info`;
CREATE TABLE `task_info`  (
                              `id` bigint(17) NOT NULL AUTO_INCREMENT COMMENT '主键',
                              `task_id` bigint(17) NULL DEFAULT NULL COMMENT '任务Id',
                              `app_id` bigint(17) NULL DEFAULT NULL COMMENT '应用Id',
                              `group_id` bigint(17) NULL DEFAULT NULL COMMENT '分组Id',
                              `task_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '任务名',
                              `task_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '任务描述',
                              `task_cycle` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '执行周期（cron 表达式）',
                              `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                              `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                              PRIMARY KEY (`id`) USING BTREE,
                              UNIQUE INDEX `unique_index`(`task_id`) USING BTREE COMMENT '任务Id唯一索引',
                              INDEX `app_group_task_index`(`app_id`, `group_id`, `task_id`) USING BTREE COMMENT '应用分组任务索引'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '任务信息表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
