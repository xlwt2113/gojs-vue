ALTER TABLE `device_info`
ADD COLUMN `url` VARCHAR(3000) NULL COMMENT '外部系统地址' AFTER `device_level`,
ADD COLUMN `username` VARCHAR(45) NULL COMMENT '用户名' AFTER `url`,
ADD COLUMN `password` VARCHAR(45) NULL COMMENT '密码' AFTER `username`;


INSERT INTO `sys_menu` (`menu_name`,`parent_id`,`order_num`,`path`,`component`,`is_frame`,`is_cache`,`menu_type`,`visible`,`status`,`perms`,`icon`,`create_by`,`create_time`,`update_by`,`update_time`,`remark`) VALUES ('数据同步',1,10,'syndb','device/syndb/index',1,1,'C','0','0','system:syndb:opt','table','admin','2021-06-06 10:02:47','admin','2021-06-06 10:11:16','');
