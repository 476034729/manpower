-- CREATE BY Wang Yu
-- 首页轮播配置表
CREATE TABLE `t_hrm_loop_play_config` (
  `id` varchar(32) NOT NULL,
  `img_id` varchar(32) NOT NULL COMMENT '图片资源表ID',
  `title` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `order` int(3) DEFAULT NULL COMMENT '顺序',
  `create_date` datetime DEFAULT NULL,
  `create_user` varchar(30) DEFAULT NULL COMMENT '创建人姓名',
  `create_id` varchar(32) DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  `modify_user` varchar(30) DEFAULT NULL COMMENT '修改人姓名',
  `modify_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;