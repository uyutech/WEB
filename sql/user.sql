

/*==============================================================*/
/* Table: user_profile                                              */
/*==============================================================*/
drop table if exists user_profile;
create table user_profile
(
   uid                    int(11) not null auto_increment ,
   status                 tinyint(1) not null default 1,
   allow_follow           tinyint(1) not null default 1,
   reg_Stat               tinyint(1) not null default 0,

   nick_name              varchar(200) not null,
   head_url               varchar(500) not null,
   author_id              int(11) not null default 0  ,
   gender                 tinyint(1) not null default 0,
   is_vip                 tinyint(1) not null default 0,
   create_time            datetime,
   modify_time            datetime,

   primary key (uid)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 ;





/*==============================================================*/
/* Table: user_open_account   个人账户基本信息                                           */
/*==============================================================*/
drop table if exists user_open_account;
create table user_open_account
(
   open_Id                varchar(200) not null,
   uid                    int(11) not null ,
   token                  varchar(200) not null,
   channel_type           tinyint(1) not null default 0,
   status                 tinyint(1) not null default 1,
   create_time            datetime,
   modify_time            datetime,
   primary key (open_Id,channel_type)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;






/*==============================================================*/
/* Table: user_open_account  账户表                                            */
/*==============================================================*/
drop table if exists user_open_account;
create table user_open_account
(
   open_Id                varchar(200) not null,
   uid                    int(11) not null ,
   token                  varchar(200) not null,
   channel_type           tinyint(1) not null default 0,
   status                 tinyint(1) not null default 1,
   is_vip                 tinyint(1) not null default 0,
   create_time            datetime,
   modify_time            datetime,
   primary key (open_Id,channel_type)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;




/*==============================================================*/
/* Table: user_fav  用户收藏                                            */
/*==============================================================*/
drop table if exists user_fav;
create table user_fav
(
   uid                    int(11) not null,
   fav_group_id           int(11) not null default 0 ,
   work_id                int(11) not null,
   status                 tinyint(1) not null default 1,
   create_time            datetime,
   modify_time            datetime,
   primary key (uid,work_id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;



/*==============================================================*/
/* Table: user_fav_group  用户收藏分组表                                           */
/*==============================================================*/
drop table if exists user_fav_group;
create table user_fav_group
(
   group_id               int(11) not null auto_increment ,
   group_name             varchar(200) not null,
   uid                    int(11) not null,
   status                 tinyint(1) not null default 1,
   create_time            datetime,
   modify_time            datetime,
   primary key (group_id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;



   
/*==============================================================*/
/* Table: user_follow_author  用户关注作者                                          */
/*==============================================================*/
drop table if exists user_follow_author;
create table user_follow_author
(
   uid                    int(11) not null,
   follow_author_id       int(11) not null,
   status                 tinyint(1) not null default 1,
   create_time            datetime,
   modify_time            datetime,
   primary key (uid,follow_author_id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;

  

/*==============================================================*/
/* Table: user_follow_tag  用户关注标签                                         */
/*==============================================================*/
drop table if exists user_follow_tag;
create table user_follow_tag
(
   uid                    int(11) not null,
   tag_id                 bigint(20) not null,
   tag_type               int(4) not null default 1,
   status                 tinyint(1) not null default 1,
   create_time            datetime,
   modify_time            datetime,
   primary key (uid,tag_id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;




/*==============================================================*/
/* Table: user_upvote_work  用户对作品点赞                                         */
/*==============================================================*/
drop table if exists user_upvote_work;
create table user_upvote_work
(
   uid                    int(11) not null,
   work_id                int(11) not null,
   status                 tinyint(1) not null default 1,
   primary key (uid,work_id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;






/*==============================================================*/
/* Table: p_tag  tag标签定义表                                         */
/*==============================================================*/
drop table if exists p_tag;
create table p_tag
(
   tag_id                 bigint(20) not null auto_increment ,
   tag_name               varchar(100) not null,
   cited_num              int(11) not null,
   tag_type               int(4) not null,
   father_tag_id          bigint(20) not null,
   primary key (tag_id),
   UNIQUE KEY unq_p_tag_name (tag_name)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;







