/*
Navicat MySQL Data Transfer

Source Server         : local3306
Source Server Version : 50022
Source Host           : localhost:3306
Source Database       : oa

Target Server Type    : MYSQL
Target Server Version : 50022
File Encoding         : 65001

Date: 2014-05-28 18:49:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `oa_leave`
-- ----------------------------
DROP TABLE IF EXISTS `oa_leave`;
CREATE TABLE `oa_leave` (
  `id` varchar(32) NOT NULL COMMENT '编号',
  `process_instance_id` varchar(32) default NULL COMMENT '流程实例编号',
  `start_time` datetime default NULL COMMENT '开始时间',
  `end_time` datetime default NULL COMMENT '结束时间',
  `leave_type` varchar(20) default NULL COMMENT '请假类型',
  `reason` varchar(255) default NULL COMMENT '请假理由',
  `apply_time` datetime default NULL COMMENT '申请时间',
  `reality_start_time` datetime default NULL COMMENT '实际开始时间',
  `reality_end_time` datetime default NULL COMMENT '实际结束时间',
  `process_status` varchar(50) default NULL COMMENT '流程状态',
  `create_by` varchar(64) default NULL COMMENT '创建者',
  `create_date` datetime default NULL COMMENT '创建时间',
  `update_by` varchar(64) default NULL COMMENT '更新者',
  `update_date` datetime default NULL COMMENT '更新时间',
  `remarks` varchar(255) default NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL default '0' COMMENT '删除标记',
  PRIMARY KEY  (`id`),
  KEY `oa_leave_create_by` (`create_by`),
  KEY `oa_leave_process_instance_id` (`process_instance_id`),
  KEY `oa_leave_del_flag` (`del_flag`),
  KEY `oa_leave_id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='请假流程表';

-- ----------------------------
-- Records of oa_leave
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_area`
-- ----------------------------
DROP TABLE IF EXISTS `sys_area`;
CREATE TABLE `sys_area` (
  `id` char(32) NOT NULL COMMENT '编号',
  `parent_id` char(32) NOT NULL COMMENT '父级编号',
  `parent_ids` varchar(2000) NOT NULL COMMENT '所有父级编号',
  `code` varchar(100) default NULL COMMENT '区域编码',
  `name` varchar(100) NOT NULL COMMENT '区域名称',
  `type` char(1) default NULL COMMENT '区域类型',
  `create_by` varchar(64) default NULL COMMENT '创建者',
  `create_time` datetime default NULL COMMENT '创建时间',
  `update_by` varchar(64) default NULL COMMENT '更新者',
  `update_time` datetime default NULL COMMENT '更新时间',
  `remarks` varchar(255) default NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL default '0' COMMENT '删除标记',
  PRIMARY KEY  (`id`),
  KEY `sys_area_parent_id` (`parent_id`),
  KEY `sys_area_parent_ids` (`parent_ids`(255)),
  KEY `sys_area_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='区域表';

-- ----------------------------
-- Records of sys_area
-- ----------------------------
INSERT INTO `sys_area` VALUES ('1', '0', '0,', '100000', '中国', '1', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_area` VALUES ('10', '8', '0,1,2,', '370532', '历城区', '4', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_area` VALUES ('11', '8', '0,1,2,', '370533', '历城区', '4', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_area` VALUES ('12', '8', '0,1,2,', '370534', '历下区', '4', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_area` VALUES ('13', '8', '0,1,2,', '370535', '天桥区', '4', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_area` VALUES ('14', '8', '0,1,2,', '370536', '槐荫区', '4', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_area` VALUES ('2', '1', '0,1,', '110000', '北京市', '2', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_area` VALUES ('3', '2', '0,1,2,', '110101', '东城区', '4', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_area` VALUES ('4', '2', '0,1,2,', '110102', '西城区', '4', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_area` VALUES ('5', '2', '0,1,2,', '110103', '朝阳区', '4', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_area` VALUES ('6', '2', '0,1,2,', '110104', '丰台区', '4', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_area` VALUES ('7', '2', '0,1,2,', '110105', '海淀区', '4', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_area` VALUES ('8', '1', '0,1,', '370000', '山东省', '2', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_area` VALUES ('9', '8', '0,1,2,', '370531', '济南市', '3', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');

-- ----------------------------
-- Table structure for `sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` char(32) NOT NULL COMMENT '编号',
  `parent_id` char(32) NOT NULL COMMENT '父级编号',
  `parent_ids` varchar(2000) NOT NULL COMMENT '所有父级编号',
  `name` varchar(100) NOT NULL COMMENT '菜单名称',
  `href` varchar(255) default NULL COMMENT '链接',
  `target` varchar(20) default NULL COMMENT '目标',
  `icon` varchar(100) default NULL COMMENT '图标',
  `sort` int(11) NOT NULL COMMENT '排序（升序）',
  `is_show` char(1) NOT NULL COMMENT '是否在菜单中显示',
  `is_activiti` char(1) default NULL COMMENT '是否同步工作流',
  `permission` varchar(200) default NULL COMMENT '权限标识',
  `create_by` varchar(64) default NULL COMMENT '创建者',
  `create_time` datetime default NULL COMMENT '创建时间',
  `update_by` varchar(64) default NULL COMMENT '更新者',
  `update_time` datetime default NULL COMMENT '更新时间',
  `remarks` varchar(255) default NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL default '0' COMMENT '删除标记',
  PRIMARY KEY  (`id`),
  KEY `sys_menu_parent_id` (`parent_id`),
  KEY `sys_menu_parent_ids` (`parent_ids`(255)),
  KEY `sys_menu_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('06451f0b27b4103295b177fb85a9c0c5', 'e321c6662652103295b177fb85a9c0c5', '0,1,44f3448920d010328a0479b72cb403f0,e321c6662652103295b177fb85a9c0c5,', '删除', '/sys/area/delete/**', '', '', '300', '0', '1', 'sys:area:delete', null, null, null, null, null, '0');
INSERT INTO `sys_menu` VALUES ('073d631227cb103295b177fb85a9c0c5', 'd5b8beaf20d310328a0479b72cb403f0', '0,1,b7dff5e820d310328a0479b72cb403f0,d5b8beaf20d310328a0479b72cb403f0,', '删除', '/sys/role/delete', '', '', '300', '0', '1', 'sys:role:delete', null, null, null, null, null, '0');
INSERT INTO `sys_menu` VALUES ('1', '0', '0,', '顶级菜单', null, null, null, '0', '0', '0', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_menu` VALUES ('14a4b5ce27b3103295b177fb85a9c0c5', 'e321c6662652103295b177fb85a9c0c5', '0,1,44f3448920d010328a0479b72cb403f0,e321c6662652103295b177fb85a9c0c5,', '添加', '/sys/area/create', '', '', '100', '0', '1', 'sys:area:create', null, null, null, null, null, '0');
INSERT INTO `sys_menu` VALUES ('1774038220d410328a0479b72cb403f0', 'b7dff5e820d310328a0479b72cb403f0', '0,1,b7dff5e820d310328a0479b72cb403f0,', '菜单管理', '/sys/menu', '', 'list', '20', '1', '1', 'sys:menu:view', null, null, null, null, null, '0');
INSERT INTO `sys_menu` VALUES ('248aac5227ca103295b177fb85a9c0c5', '586d873e20d010328a0479b72cb403f0', '0,1,44f3448920d010328a0479b72cb403f0,586d873e20d010328a0479b72cb403f0,', '删除', '/sys/user/delete', '', '', '300', '0', '1', 'sys:user:delete', null, null, null, null, null, '0');
INSERT INTO `sys_menu` VALUES ('302cc2e32d47103295b177fb85a9c0c5', '44f3448920d010328a0479b72cb403f0', '0,1,44f3448920d010328a0479b72cb403f0,', '机构管理', '/sys/org', '', 'th-large', '20', '1', '1', 'sys:org:view', null, null, null, null, null, '0');
INSERT INTO `sys_menu` VALUES ('44f3448920d010328a0479b72cb403f0', '1', '0,1,', '机构用户', '', '', '', '2', '1', '1', '', null, null, null, null, null, '0');
INSERT INTO `sys_menu` VALUES ('46b0b6cd37831032a742ebdf985f1138', '1', '0,1,', '在线办公', '', '', '', '1', '1', '1', '', null, null, null, null, null, '0');
INSERT INTO `sys_menu` VALUES ('586d873e20d010328a0479b72cb403f0', '44f3448920d010328a0479b72cb403f0', '0,1,44f3448920d010328a0479b72cb403f0,', '用户管理', '/sys/user', '', 'user', '10', '1', '1', 'sys:user:view', null, null, null, null, null, '0');
INSERT INTO `sys_menu` VALUES ('5c4ad12a27ca103295b177fb85a9c0c5', '1774038220d410328a0479b72cb403f0', '0,1,b7dff5e820d310328a0479b72cb403f0,1774038220d410328a0479b72cb403f0,', '添加', '/sys/menu/create', '', '', '100', '0', '1', 'sys:menu:create', null, null, null, null, null, '0');
INSERT INTO `sys_menu` VALUES ('78e2582a37831032a742ebdf985f1138', '46b0b6cd37831032a742ebdf985f1138', '0,1,46b0b6cd37831032a742ebdf985f1138,', '请假申请', '/oa/leave', '', 'arrow-right', '10', '1', '1', 'oa:leave:view', null, null, null, null, null, '0');
INSERT INTO `sys_menu` VALUES ('7b67019827ca103295b177fb85a9c0c5', '1774038220d410328a0479b72cb403f0', '0,1,b7dff5e820d310328a0479b72cb403f0,1774038220d410328a0479b72cb403f0,', '修改', '/sys/menu/update/**', '', '', '200', '0', '1', 'sys:menu:edit', null, null, null, null, null, '0');
INSERT INTO `sys_menu` VALUES ('937931692d58103295b177fb85a9c0c5', '302cc2e32d47103295b177fb85a9c0c5', '0,1,44f3448920d010328a0479b72cb403f0,302cc2e32d47103295b177fb85a9c0c5,', '添加', '/sys/org/create', '', '', '100', '0', '1', 'sys:org:create', null, null, null, null, null, '0');
INSERT INTO `sys_menu` VALUES ('a96e04ca2d58103295b177fb85a9c0c5', '302cc2e32d47103295b177fb85a9c0c5', '0,1,44f3448920d010328a0479b72cb403f0,302cc2e32d47103295b177fb85a9c0c5,', '更新', '/sys/org/update/**', '', '', '200', '0', '1', 'sys:org:edit', null, null, null, null, null, '0');
INSERT INTO `sys_menu` VALUES ('b7dff5e820d310328a0479b72cb403f0', '1', '0,1,', '系统设置', '', '', '', '2', '1', '1', '', null, null, null, null, null, '0');
INSERT INTO `sys_menu` VALUES ('bcfc297a27ca103295b177fb85a9c0c5', '1774038220d410328a0479b72cb403f0', '0,1,b7dff5e820d310328a0479b72cb403f0,1774038220d410328a0479b72cb403f0,', '删除', '/sys/menu/delete/**', '', '', '300', '0', '1', 'sys:menu:delete', null, null, null, null, null, '0');
INSERT INTO `sys_menu` VALUES ('bf9eeb312d58103295b177fb85a9c0c5', '302cc2e32d47103295b177fb85a9c0c5', '0,1,44f3448920d010328a0479b72cb403f0,302cc2e32d47103295b177fb85a9c0c5,', '删除', '/sys/org/delete/**', '', '', '300', '0', '1', 'sys:org:delete', null, null, null, null, null, '0');
INSERT INTO `sys_menu` VALUES ('d2fcb2d120d210328a0479b72cb403f0', '586d873e20d010328a0479b72cb403f0', '0,1,44f3448920d010328a0479b72cb403f0,586d873e20d010328a0479b72cb403f0,', '添加', '/sys/user/create', '', '', '100', '0', '1', 'sys:user:create', null, null, null, null, null, '0');
INSERT INTO `sys_menu` VALUES ('d5b8beaf20d310328a0479b72cb403f0', 'b7dff5e820d310328a0479b72cb403f0', '0,1,b7dff5e820d310328a0479b72cb403f0,', '角色管理', '/sys/role', '', 'lock', '10', '1', '1', 'sys:role:view', null, null, null, null, null, '0');
INSERT INTO `sys_menu` VALUES ('de9d07a527ca103295b177fb85a9c0c5', 'd5b8beaf20d310328a0479b72cb403f0', '0,1,b7dff5e820d310328a0479b72cb403f0,d5b8beaf20d310328a0479b72cb403f0,', '添加', '/sys/role/create', '', '', '100', '0', '1', 'sys:role:create', null, null, null, null, null, '0');
INSERT INTO `sys_menu` VALUES ('df97389a27b3103295b177fb85a9c0c5', 'e321c6662652103295b177fb85a9c0c5', '0,1,44f3448920d010328a0479b72cb403f0,e321c6662652103295b177fb85a9c0c5,', '修改', '/sys/area/update/**', '', '', '200', '0', '1', 'sys:area:edit', null, null, null, null, null, '0');
INSERT INTO `sys_menu` VALUES ('e321c6662652103295b177fb85a9c0c5', '44f3448920d010328a0479b72cb403f0', '0,1,44f3448920d010328a0479b72cb403f0,', '区域管理', '/sys/area', '', 'flag', '30', '1', '1', 'sys:area:view', null, null, null, null, null, '0');
INSERT INTO `sys_menu` VALUES ('ebff98e020d210328a0479b72cb403f0', '586d873e20d010328a0479b72cb403f0', '0,1,44f3448920d010328a0479b72cb403f0,586d873e20d010328a0479b72cb403f0,', '更新', '/sys/user/update/**', '', '', '200', '0', '1', 'sys:user:edit', null, null, null, null, null, '0');
INSERT INTO `sys_menu` VALUES ('efc00fd127cb103295b177fb85a9c0c5', 'd5b8beaf20d310328a0479b72cb403f0', '0,1,b7dff5e820d310328a0479b72cb403f0,d5b8beaf20d310328a0479b72cb403f0,', '修改', '/sys/role/update/**', '', '', '200', '0', '1', 'sys:role:edit', null, null, null, null, null, '0');

-- ----------------------------
-- Table structure for `sys_org`
-- ----------------------------
DROP TABLE IF EXISTS `sys_org`;
CREATE TABLE `sys_org` (
  `id` char(32) NOT NULL COMMENT '编号',
  `parent_id` char(32) NOT NULL COMMENT '父级编号',
  `parent_ids` varchar(2000) NOT NULL COMMENT '所有父级编号',
  `area_id` char(32) NOT NULL COMMENT '归属区域',
  `code` varchar(100) default NULL COMMENT '区域编码',
  `name` varchar(100) NOT NULL COMMENT '机构名称',
  `type` char(1) NOT NULL COMMENT '机构类型',
  `grade` char(1) NOT NULL COMMENT '机构等级',
  `address` varchar(255) default NULL COMMENT '联系地址',
  `zip_code` varchar(100) default NULL COMMENT '邮政编码',
  `master` varchar(100) default NULL COMMENT '负责人',
  `phone` varchar(200) default NULL COMMENT '电话',
  `fax` varchar(200) default NULL COMMENT '传真',
  `email` varchar(200) default NULL COMMENT '邮箱',
  `create_by` varchar(64) default NULL COMMENT '创建者',
  `create_date` datetime default NULL COMMENT '创建时间',
  `update_by` varchar(64) default NULL COMMENT '更新者',
  `update_date` datetime default NULL COMMENT '更新时间',
  `remarks` varchar(255) default NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL default '0' COMMENT '删除标记',
  PRIMARY KEY  (`id`),
  KEY `sys_office_parent_id` (`parent_id`),
  KEY `sys_office_parent_ids` (`parent_ids`(255)),
  KEY `sys_office_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='机构表';

-- ----------------------------
-- Records of sys_org
-- ----------------------------
INSERT INTO `sys_org` VALUES ('1', '0', '0,', '2', '100000', '北京市总公司', '1', '1', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_org` VALUES ('10', '7', '0,1,7,', '8', '200003', '市场部', '2', '2', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_org` VALUES ('11', '7', '0,1,7,', '8', '200004', '技术部', '2', '2', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_org` VALUES ('12', '7', '0,1,7,', '9', '201000', '济南市分公司', '1', '3', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_org` VALUES ('13', '12', '0,1,7,12,', '9', '201001', '公司领导', '2', '3', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_org` VALUES ('14', '12', '0,1,7,12,', '9', '201002', '综合部', '2', '3', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_org` VALUES ('15', '12', '0,1,7,12,', '9', '201003', '市场部', '2', '3', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_org` VALUES ('16', '12', '0,1,7,12,', '9', '201004', '技术部', '2', '3', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_org` VALUES ('17', '12', '0,1,7,12,', '11', '201010', '济南市历城区分公司', '1', '4', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_org` VALUES ('18', '17', '0,1,7,12,17,', '11', '201011', '公司领导', '2', '4', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_org` VALUES ('19', '17', '0,1,7,12,17,', '11', '201012', '综合部', '2', '4', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_org` VALUES ('2', '1', '0,1,', '2', '100001', '公司领导', '2', '4', '', '', '', '', '', '', '1', '2013-05-27 08:00:00', '2', '2014-03-13 11:41:18', '', '0');
INSERT INTO `sys_org` VALUES ('20', '17', '0,1,7,12,17,', '11', '201013', '市场部', '2', '4', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_org` VALUES ('21', '17', '0,1,7,12,17,', '11', '201014', '技术部', '2', '4', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_org` VALUES ('22', '12', '0,1,7,12,', '12', '201020', '济南市历下区分公司', '1', '4', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_org` VALUES ('23', '22', '0,1,7,12,22,', '12', '201021', '公司领导', '2', '4', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_org` VALUES ('24', '22', '0,1,7,12,22,', '12', '201022', '综合部', '2', '4', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_org` VALUES ('25', '22', '0,1,7,12,22,', '12', '201023', '市场部', '2', '4', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_org` VALUES ('26', '22', '0,1,7,12,22,', '12', '201024', '技术部', '2', '4', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_org` VALUES ('3', '1', '0,1,', '2', '100002', '人力部', '2', '1', '', '', '', '', '', '', '1', '2013-05-27 08:00:00', '2', '2014-03-13 11:41:35', '', '0');
INSERT INTO `sys_org` VALUES ('4', '1', '0,1,', '2', '100003', '市场部', '2', '1', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_org` VALUES ('5', '1', '0,1,', '2', '100004', '技术部', '2', '1', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_org` VALUES ('6', '1', '0,1,', '2', '100005', '研发部', '2', '1', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_org` VALUES ('7', '1', '0,1,', '8', '200000', '山东省分公司', '1', '2', '', '', '', '', '', '', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_org` VALUES ('8', '7', '0,1,7,', '8', '200001', '公司领导', '2', '2', '', '', '', '', '', '', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_org` VALUES ('9', '7', '0,1,7,', '8', '200002', '综合部', '2', '2', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');

-- ----------------------------
-- Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` char(32) NOT NULL COMMENT '编号',
  `office_id` varchar(64) default NULL COMMENT '归属机构',
  `name` varchar(100) NOT NULL COMMENT '角色名称',
  `data_scope` char(1) default NULL COMMENT '数据范围',
  `create_by` varchar(64) default NULL COMMENT '创建者',
  `create_time` datetime default NULL COMMENT '创建时间',
  `update_by` varchar(64) default NULL COMMENT '更新者',
  `update_time` datetime default NULL COMMENT '更新时间',
  `remarks` varchar(255) default NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL default '0' COMMENT '删除标记',
  PRIMARY KEY  (`id`),
  KEY `sys_role_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('59bc0b3d1ce710328a0479b72cb403f0', null, '超级管理员', '1', null, null, null, null, null, '0');
INSERT INTO `sys_role` VALUES ('d6c3bdbc1ceb10328a0479b72cb403f0', null, '普通用户', '1', null, null, null, null, null, '0');

-- ----------------------------
-- Table structure for `sys_role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `role_id` varchar(64) NOT NULL COMMENT '角色编号',
  `menu_id` varchar(64) NOT NULL COMMENT '菜单编号',
  PRIMARY KEY  (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色-菜单';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('59bc0b3d1ce710328a0479b72cb403f0', '06451f0b27b4103295b177fb85a9c0c5');
INSERT INTO `sys_role_menu` VALUES ('59bc0b3d1ce710328a0479b72cb403f0', '073d631227cb103295b177fb85a9c0c5');
INSERT INTO `sys_role_menu` VALUES ('59bc0b3d1ce710328a0479b72cb403f0', '1');
INSERT INTO `sys_role_menu` VALUES ('59bc0b3d1ce710328a0479b72cb403f0', '14a4b5ce27b3103295b177fb85a9c0c5');
INSERT INTO `sys_role_menu` VALUES ('59bc0b3d1ce710328a0479b72cb403f0', '1774038220d410328a0479b72cb403f0');
INSERT INTO `sys_role_menu` VALUES ('59bc0b3d1ce710328a0479b72cb403f0', '248aac5227ca103295b177fb85a9c0c5');
INSERT INTO `sys_role_menu` VALUES ('59bc0b3d1ce710328a0479b72cb403f0', '302cc2e32d47103295b177fb85a9c0c5');
INSERT INTO `sys_role_menu` VALUES ('59bc0b3d1ce710328a0479b72cb403f0', '44f3448920d010328a0479b72cb403f0');
INSERT INTO `sys_role_menu` VALUES ('59bc0b3d1ce710328a0479b72cb403f0', '46b0b6cd37831032a742ebdf985f1138');
INSERT INTO `sys_role_menu` VALUES ('59bc0b3d1ce710328a0479b72cb403f0', '586d873e20d010328a0479b72cb403f0');
INSERT INTO `sys_role_menu` VALUES ('59bc0b3d1ce710328a0479b72cb403f0', '5c4ad12a27ca103295b177fb85a9c0c5');
INSERT INTO `sys_role_menu` VALUES ('59bc0b3d1ce710328a0479b72cb403f0', '78e2582a37831032a742ebdf985f1138');
INSERT INTO `sys_role_menu` VALUES ('59bc0b3d1ce710328a0479b72cb403f0', '7b67019827ca103295b177fb85a9c0c5');
INSERT INTO `sys_role_menu` VALUES ('59bc0b3d1ce710328a0479b72cb403f0', '937931692d58103295b177fb85a9c0c5');
INSERT INTO `sys_role_menu` VALUES ('59bc0b3d1ce710328a0479b72cb403f0', 'a96e04ca2d58103295b177fb85a9c0c5');
INSERT INTO `sys_role_menu` VALUES ('59bc0b3d1ce710328a0479b72cb403f0', 'b7dff5e820d310328a0479b72cb403f0');
INSERT INTO `sys_role_menu` VALUES ('59bc0b3d1ce710328a0479b72cb403f0', 'bcfc297a27ca103295b177fb85a9c0c5');
INSERT INTO `sys_role_menu` VALUES ('59bc0b3d1ce710328a0479b72cb403f0', 'bf9eeb312d58103295b177fb85a9c0c5');
INSERT INTO `sys_role_menu` VALUES ('59bc0b3d1ce710328a0479b72cb403f0', 'd2fcb2d120d210328a0479b72cb403f0');
INSERT INTO `sys_role_menu` VALUES ('59bc0b3d1ce710328a0479b72cb403f0', 'd5b8beaf20d310328a0479b72cb403f0');
INSERT INTO `sys_role_menu` VALUES ('59bc0b3d1ce710328a0479b72cb403f0', 'de9d07a527ca103295b177fb85a9c0c5');
INSERT INTO `sys_role_menu` VALUES ('59bc0b3d1ce710328a0479b72cb403f0', 'df97389a27b3103295b177fb85a9c0c5');
INSERT INTO `sys_role_menu` VALUES ('59bc0b3d1ce710328a0479b72cb403f0', 'e321c6662652103295b177fb85a9c0c5');
INSERT INTO `sys_role_menu` VALUES ('59bc0b3d1ce710328a0479b72cb403f0', 'ebff98e020d210328a0479b72cb403f0');
INSERT INTO `sys_role_menu` VALUES ('59bc0b3d1ce710328a0479b72cb403f0', 'efc00fd127cb103295b177fb85a9c0c5');
INSERT INTO `sys_role_menu` VALUES ('d6c3bdbc1ceb10328a0479b72cb403f0', '1');
INSERT INTO `sys_role_menu` VALUES ('d6c3bdbc1ceb10328a0479b72cb403f0', '14a4b5ce27b3103295b177fb85a9c0c5');
INSERT INTO `sys_role_menu` VALUES ('d6c3bdbc1ceb10328a0479b72cb403f0', '1774038220d410328a0479b72cb403f0');
INSERT INTO `sys_role_menu` VALUES ('d6c3bdbc1ceb10328a0479b72cb403f0', '44f3448920d010328a0479b72cb403f0');
INSERT INTO `sys_role_menu` VALUES ('d6c3bdbc1ceb10328a0479b72cb403f0', '586d873e20d010328a0479b72cb403f0');
INSERT INTO `sys_role_menu` VALUES ('d6c3bdbc1ceb10328a0479b72cb403f0', '5c4ad12a27ca103295b177fb85a9c0c5');
INSERT INTO `sys_role_menu` VALUES ('d6c3bdbc1ceb10328a0479b72cb403f0', 'b7dff5e820d310328a0479b72cb403f0');
INSERT INTO `sys_role_menu` VALUES ('d6c3bdbc1ceb10328a0479b72cb403f0', 'd2fcb2d120d210328a0479b72cb403f0');
INSERT INTO `sys_role_menu` VALUES ('d6c3bdbc1ceb10328a0479b72cb403f0', 'd5b8beaf20d310328a0479b72cb403f0');
INSERT INTO `sys_role_menu` VALUES ('d6c3bdbc1ceb10328a0479b72cb403f0', 'de9d07a527ca103295b177fb85a9c0c5');
INSERT INTO `sys_role_menu` VALUES ('d6c3bdbc1ceb10328a0479b72cb403f0', 'e321c6662652103295b177fb85a9c0c5');

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` char(32) NOT NULL COMMENT '编号',
  `company_id` char(32) default NULL COMMENT '归属公司',
  `office_id` char(32) default NULL COMMENT '归属部门',
  `login_name` varchar(100) NOT NULL COMMENT '登录名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `salt` varchar(64) default NULL,
  `no` varchar(100) default NULL COMMENT '工号',
  `name` varchar(100) NOT NULL COMMENT '姓名',
  `email` varchar(200) default NULL COMMENT '邮箱',
  `phone` varchar(200) default NULL COMMENT '电话',
  `mobile` varchar(200) default NULL COMMENT '手机',
  `user_type` char(1) default NULL COMMENT '用户类型',
  `login_ip` varchar(100) default NULL COMMENT '最后登陆IP',
  `login_time` datetime default NULL COMMENT '最后登陆时间',
  `create_by` varchar(64) default NULL COMMENT '创建者',
  `create_time` datetime default NULL COMMENT '创建时间',
  `update_by` varchar(64) default NULL COMMENT '更新者',
  `update_time` datetime default NULL COMMENT '更新时间',
  `remarks` varchar(255) default NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL default '0' COMMENT '删除标记',
  PRIMARY KEY  (`id`),
  KEY `sys_user_office_id` (`office_id`),
  KEY `sys_user_login_name` (`login_name`),
  KEY `sys_user_company_id` (`company_id`),
  KEY `sys_user_update_date` (`update_time`),
  KEY `sys_user_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', null, null, 'admin', '19edac50b7db81675a5b7b1cf25125d05ff90ced', '25d1e6c2ec08de66', null, '唐欣', 'tangxin983@163.com', '87710815', '13645055562', null, null, null, null, null, null, null, null, '0');
INSERT INTO `sys_user` VALUES ('ea3b043927c9103295b177fb85a9c0c5', null, null, 'user', '21bf009ccb875f7229929da5343ff31050bd0cb0', '745db3490ed168e5', null, '小白', '', '', '', null, null, null, null, null, null, null, null, '0');

-- ----------------------------
-- Table structure for `sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` varchar(64) NOT NULL COMMENT '用户编号',
  `role_id` varchar(64) NOT NULL COMMENT '角色编号',
  PRIMARY KEY  (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户-角色';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '59bc0b3d1ce710328a0479b72cb403f0');
INSERT INTO `sys_user_role` VALUES ('1', 'd6c3bdbc1ceb10328a0479b72cb403f0');
INSERT INTO `sys_user_role` VALUES ('ea3b043927c9103295b177fb85a9c0c5', 'd6c3bdbc1ceb10328a0479b72cb403f0');
