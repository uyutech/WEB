



  /*==============================================================*/
/* Table: suggest_source_mgr 发现页 推荐的sourcetpye类型管理                                      */
/*==============================================================*/
drop table if exists suggest_source_mgr;
create table suggest_source_mgr
(
   batch_num              int(11) not null ,
   source_type            varchar(100) not null,  
   status                 tinyint(1) not null default 1,
   create_time            datetime,
   modify_time            datetime,
   primary key (batch_num,source_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;
  



/*==============================================================*/
/* Table: register_appointment 预约注册用户信息                                      */
/*==============================================================*/
drop table if exists register_appointment;
create table register_appointment
(
   id                     int(11) not null auto_increment ,
   channel_type           tinyint(1) not null default 0,   
   open_Id                varchar(200) not null,
   token                  varchar(200) not null,
   is_sync_data           tinyint(1) not null default 1,
   create_time            datetime,
   modify_time            datetime,
   primary key (id),
   UNIQUE KEY unq_reg_appoint_type_openid (channel_type,open_Id),  
   KEY idx_reg_appoint_issync (is_sync_data)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;
  


 /*==============================================================*/
/* Table: weibo_register_appointment_user_data 微博预约用户的基础信息                                     */
/*==============================================================*/
drop table if exists weibo_register_appointment_user_data;
create table weibo_register_appointment_user_data
(
   open_Id                varchar(200) not null,
   name                   varchar(200) not null default '',  
   screen_name            varchar(200) not null default '',  
   url                    varchar(200) ,  
   wei_hao                varchar(200) ,  
   profile_url            varchar(200) not null default '',  

   profile_image_url      varchar(200) not null default '',  
   domain                 varchar(200) ,  
   gender                 varchar(10) not null default '',  
   location               varchar(200) ,  
   description            varchar(200) ,  


   city                   int(11) not null default 0,
   province               int(11) not null default 0,
  
   statuses_count         int(11) not null default 0,
   favourites_count       int(11) not null default 0,
   followers_count        int(11) not null default 0,
   friends_count          int(11) not null default 0,
   
   created_at             varchar(50) not null,

   primary key (open_Id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;



 /*==============================================================*/
/* Table: weibo_register_appointment_user_follow_data 微博用户关注的用户id信息                                     */
/*==============================================================*/
drop table if exists weibo_register_appointment_user_follow_data;
create table weibo_register_appointment_user_follow_data
(
   open_id                varchar(200) not null,
   followed_open_id       varchar(200) not null,  
   primary key (open_Id,followed_open_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;