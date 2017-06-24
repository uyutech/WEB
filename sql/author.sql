
use zqdb;
/*==============================================================*/
/* Table: author_base    作者基本信息                                          */
/*==============================================================*/
drop table if exists author_base;
create table author_base
(
   author_id              int(11) not null auto_increment ,
   author_name            varchar(200) not null,
   head_url               varchar(200) not null,
   fans_num               int(11) not null default 0,
   status                 tinyint(1) not null default 1,
   create_time            datetime,
   modify_time            datetime,
   primary key (author_id)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 ;




/*==============================================================*/
/* Table: author_tag_mapping    作者标签映射表                                          */
/*==============================================================*/
drop table if exists author_tag_mapping;
create table author_tag_mapping
(
   author_id              int(11) not null  ,
   tag_id                 bigint(20) not null,
   tag_type               int(4) not null ,
   status                 tinyint(1) not null default 1,
   order_num              int(4) not null ,
   create_time            datetime,
   modify_time            datetime,
   primary key (author_id,tag_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;





/*==============================================================*/
/* Table: author_work_history    作者参与作品履历记录，同一个作品可能多条记录，不同的角色                                        */
/*==============================================================*/
drop table if exists author_work_history;
create table author_work_history
(
   author_id              int(11) not null  ,
   work_id                int(11) not null,
   role_id                int(4) not null default 0 ,
   refer_id               int(11) not null default 0 ,
   refer_type             int(4) not null default 0 ,
   summary                varchar(500) not null,
   status                 tinyint(1) not null default 1,
   create_time            datetime,
   modify_time            datetime,
   UNIQUE KEY uniq_work_his (author_id,work_id,role_id), 
   KEY idx_work_his_wokid (work_id),
   KEY idx_work_his_referidandtype (refer_id,refer_type,role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;





/*==============================================================*/
/* Table: author_role_define    作者角色类型定义表                                        */
/*==============================================================*/
drop table if exists author_role_define;
create table author_role_define
(
   role_id                int(11) not null auto_increment,
   role_name              varchar(100) not null,
   category               int(4) not null default 0 ,
   status                 tinyint(1) not null default 1,
   create_time            datetime,
   modify_time            datetime,
   primary key (role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;




/*==============================================================*/
/* Table: author_hot_indexes   作者热度的推荐                                    */
/*==============================================================*/
drop table if exists author_hot_indexes;
create table author_hot_indexes
(
   author_id              int(11) not null  ,
   hot_index             int(11) not null  ,
   create_time            datetime,
   modify_time            datetime,
   primary key (author_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;







/*==============================================================*/
/* Table: author_dynamics    作者动态表                                       */
/*==============================================================*/
drop table if exists author_dynamics;
create table author_dynamics
(
   id                     int(11) not null auto_increment ,
   author_id              int(11) not null  ,
   oper_type              int(4) not null default 0 ,
   platform_id            int(11) not null ,
   target                 varchar(200) not null,
   extend_info            varchar(500) not null default '',
   status                 tinyint(1) not null default 1,
   publish_time           datetime,
   create_time            datetime,
   modify_time            datetime,
   primary key (id),
   KEY idx_authdyna_all (author_id,platform_id,oper_type),
   KEY idx_authdyna_pubtime(publish_time)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;





/*==============================================================*/
/* Table: author_third_platform_define    第三方平台的信息定义                                     */
/*==============================================================*/
drop table if exists author_third_platform_define;
create table author_third_platform_define
(
   id                     int(11) not null auto_increment ,
   type                   int(4) not null default 0 ,
   name                   varchar(200) not null,
   logo_url               varchar(500) not null default '',
   status                 tinyint(1) not null default 1,
   create_time            datetime,
   modify_time            datetime,
   primary key (id)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;




