
/*==============================================================*/
/* create database and set user auth                                            */
/*==============================================================*/
create database zqdb;

grant select,insert,update,delete on zqdb.* to zhuanquan@"%" identified by "Qwer!234";




