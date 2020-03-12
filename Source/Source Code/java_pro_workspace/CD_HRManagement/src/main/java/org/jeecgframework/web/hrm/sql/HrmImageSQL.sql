-- CREATE BY Wang Yu
-- 图片资源表
CREATE TABLE `t_hrm_image_resource` (
  `id` varchar(32) NOT NULL,
  `url` varchar(255) NOT NULL COMMENT '图片地址',
  `create_user` varchar(30) DEFAULT NULL,
  `create_id` varchar(32) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `modify_user` varchar(30) DEFAULT NULL,
  `modify_id` varchar(32) DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;