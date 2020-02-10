/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50549
Source Host           : localhost:3306
Source Database       : mybatis-plus-high

Target Server Type    : MYSQL
Target Server Version : 50549
File Encoding         : 65001

Date: 2020-02-10 11:30:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for profile
-- ----------------------------
DROP TABLE IF EXISTS `profile`;
CREATE TABLE `profile` (
  `id` bigint(20) NOT NULL,
  `manager_id` bigint(20) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of profile
-- ----------------------------
INSERT INTO `profile` VALUES ('1', '1224581991756791809', '1号档案', '1号档案');
INSERT INTO `profile` VALUES ('2', '1224581991765180418', '2号档案', '2号档案');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(30) DEFAULT NULL COMMENT '姓名',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `manager_id` bigint(10) DEFAULT NULL COMMENT '直属上级id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '1' COMMENT '版本',
  `deleted` int(1) DEFAULT '0' COMMENT '逻辑删除标识(0.未删除,1.已删除)',
  PRIMARY KEY (`id`),
  KEY `manager_fk` (`manager_id`),
  CONSTRAINT `manager_fk` FOREIGN KEY (`manager_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1224581991748403201', '大王', '20', 'dawang@user.com', null, '2020-02-04 14:34:33', null, '1', '0');
INSERT INTO `user` VALUES ('1224581991756791809', '小王', '21', 'xiaowang@user.com', '1224581991748403201', '2020-02-04 14:34:33', null, '1', '0');
INSERT INTO `user` VALUES ('1224581991765180418', '王一', '29', 'wangyi@user.com', '1224581991756791809', '2020-02-04 14:34:33', '2020-02-06 11:36:36', '1', '0');
INSERT INTO `user` VALUES ('1224581991769374722', '王二', '23', 'wanger@user.com', '1224581991756791809', '2020-02-04 14:34:33', null, '1', '1');
INSERT INTO `user` VALUES ('1224912836346564609', '王三', '28', 'wangsan@user.com', '1224581991756791809', '2020-02-05 12:29:12', '2020-02-06 11:36:20', '1', '0');
INSERT INTO `user` VALUES ('1225252721980985346', '王四', '25', 'wangsi@user.com', '1224581991765180418', '2020-02-06 10:59:47', '2020-02-06 11:13:35', '1', '0');
INSERT INTO `user` VALUES ('1225261847062532097', '王五', '28', 'wangwu@user.com', '1224581991765180418', '2020-02-06 11:36:03', '2020-02-10 09:04:21', '9', '0');
INSERT INTO `user` VALUES ('1226387761511112706', '王六', '30', 'wangliu@user.com', '1224581991756791809', '2020-02-09 14:10:02', '2020-02-10 10:18:21', '3', '0');
