-- CREATE BY Wang Yu
-- 公告表
CREATE TABLE `t_hrm_announcement` (
  `id` varchar(32) NOT NULL,
  `title` varchar(255) DEFAULT NULL COMMENT '公告标题',
  `content` varchar(1024) NOT NULL COMMENT '公告内容',
  `create_date` datetime DEFAULT NULL,
  `create_user` varchar(30) DEFAULT NULL COMMENT '创建者姓名',
  `create_id` varchar(32) DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  `modify_user` varchar(30) DEFAULT NULL COMMENT '修改者姓名',
  `modify_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;