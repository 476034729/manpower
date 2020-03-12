DROP TABLE t_allowance_cfg ;
CREATE TABLE IF NOT EXISTS  `t_allowance_cfg` (
  `id` int(6) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `country_name` varchar(16) NOT NULL COMMENT '国家名称',
  `allowance_fee` int(6) NOT NULL COMMENT '补贴费用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO t_allowance_cfg (country_name,allowance_fee) VALUES ('中国国内',150); 
INSERT INTO t_allowance_cfg (country_name,allowance_fee) VALUES ('韩国',420); 
INSERT INTO t_allowance_cfg (country_name,allowance_fee) VALUES ('日本',420); 
INSERT INTO t_allowance_cfg (country_name,allowance_fee) VALUES ('沙特阿拉伯',420); 
INSERT INTO t_allowance_cfg (country_name,allowance_fee) VALUES ('阿联酋',420); 
INSERT INTO t_allowance_cfg (country_name,allowance_fee) VALUES ('印度',360); 
INSERT INTO t_allowance_cfg (country_name,allowance_fee) VALUES ('越南',300); 
INSERT INTO t_allowance_cfg (country_name,allowance_fee) VALUES ('马来西亚',300); 
INSERT INTO t_allowance_cfg (country_name,allowance_fee) VALUES ('菲律宾',300); 
INSERT INTO t_allowance_cfg (country_name,allowance_fee) VALUES ('泰国',300); 
INSERT INTO t_allowance_cfg (country_name,allowance_fee) VALUES ('新加坡',360); 
INSERT INTO t_allowance_cfg (country_name,allowance_fee) VALUES ('土耳其',300); 
INSERT INTO t_allowance_cfg (country_name,allowance_fee) VALUES ('香港',360); 
INSERT INTO t_allowance_cfg (country_name,allowance_fee) VALUES ('澳门',420); 
INSERT INTO t_allowance_cfg (country_name,allowance_fee) VALUES ('台湾',300); 
INSERT INTO t_allowance_cfg (country_name,allowance_fee) VALUES ('德国',420); 
INSERT INTO t_allowance_cfg (country_name,allowance_fee) VALUES ('意大利',420); 
INSERT INTO t_allowance_cfg (country_name,allowance_fee) VALUES ('法国',420); 
INSERT INTO t_allowance_cfg (country_name,allowance_fee) VALUES ('英国',420); 
INSERT INTO t_allowance_cfg (country_name,allowance_fee) VALUES ('美国',420); 
INSERT INTO t_allowance_cfg (country_name,allowance_fee) VALUES ('加拿大',420); 
INSERT INTO t_allowance_cfg (country_name,allowance_fee) VALUES ('巴西',300); 
INSERT INTO t_allowance_cfg (country_name,allowance_fee) VALUES ('澳大利亚',360); 
COMMIT;

DROP TABLE IF  EXISTS t_s_reimburse ;
CREATE TABLE IF NOT EXISTS  `t_s_reimburse` (
  `id` varchar(32) NOT NULL  COMMENT '主键',
  `user_id` varchar(32) NOT NULL COMMENT '关联用户主键id',
  `project_name` varchar(32) NOT NULL COMMENT '实际项目',
  `expend_name` varchar(32) NOT NULL COMMENT '支出项目',
  `apply_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`start_time` timestamp NOT NULL  COMMENT '到达出差地日期',
	`end_time` timestamp NOT NULL  COMMENT '离开出差地日期',
	`costcenter_code` VARCHAR(16) NOT NULL COMMENT '成本中心号',
	`currency` VARCHAR(16) NOT NULL COMMENT '货币类型',
	`country_name` VARCHAR(16) NOT NULL COMMENT '出差地点',
	`allowance_fee` DECIMAL(10,2) NOT NULL COMMENT '补贴金额',
	`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
	constraint `fk_user_id` foreign key (`user_id`) references `t_s_user`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF  EXISTS t_s_reim_details ;
CREATE TABLE IF NOT EXISTS  `t_s_reim_details` (
  `id` varchar(32) NOT NULL  COMMENT '主键',
  `reimburse_id` varchar(32) NOT NULL COMMENT '关联报销id',
  `create_date` date NOT NULL COMMENT '创建日期',
	`expense_type` int(2) NOT NULL  COMMENT '费用类型 1 交通 2 酒店 3 通讯  9 其它' ,
	`details` VARCHAR(255) NOT NULL COMMENT '费用描述',
	`cost_fee` DECIMAL(8,2) NOT NULL  COMMENT '费用金额',
	`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
	constraint `fk_reimburse_id` foreign key (`reimburse_id`) references `t_s_reimburse`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF  EXISTS t_s_ticket_info ;
CREATE TABLE IF NOT EXISTS  `t_s_ticket_info` (
  `id` int(8) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `business_type` varchar(32) NOT NULL COMMENT '业务类别',
  `start_site` varchar(32) NOT NULL COMMENT '出发地',
  `end_site` varchar(32) NOT NULL COMMENT '到达地',
  `arrive_date` date NOT NULL COMMENT '到达日期',
	`passenger_name` VARCHAR(32) NOT NULL  COMMENT '乘客姓名' ,
	`price` DECIMAL(10,2) NOT NULL COMMENT '票价',
	`taxs` DECIMAL(10,2) NOT NULL COMMENT '税金',
	`sum_price` DECIMAL(10,2) NOT NULL COMMENT '总价',
	`days` DECIMAL(4,2) DEFAULT NULL COMMENT '天数',
	`project_code` varchar(32) NOT NULL COMMENT '项目编号',
	`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



select * from t_allowance_cfg where country_name=' 中国国内';