

/*==============================================================*/
/* Table: work_base 作品基本信息表                                              */
/*==============================================================*/
drop table if exists work_base;
create table work_base
(
   work_id                int(11) not null auto_increment ,
   subject                varchar(200) not null,
   summary                varchar(500) not null,

   cov_pic_Url            varchar(200) not null,
   status                 tinyint(1) not null default 1,
   create_time            datetime,
   modify_time            datetime,

   primary key (work_id),
   KEY idx_base_subject (subject)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;




/*==============================================================*/
/* Table: work_base_extend 作品扩展信息表                                              */
/*==============================================================*/
drop table if exists work_base_extend;
create table work_base_extend
(
   work_id                int(11) not null ,
   extend_attr            int(4) not null ,
   attr_val               varchar(200) not null,
   order_num              int(4) not null ,
   status                 tinyint(1) not null default 1,
   remark                 varchar(200) not null,
   UNIQUE KEY uniq_work_baseex (work_id,extend_attr,attr_val)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;






 /*==============================================================*/
/* Table: work_tag_mapping 作品tag标签映射                                              */
/*==============================================================*/
drop table if exists work_tag_mapping;
create table work_tag_mapping
(
   work_id                int(11) not null  ,
   tag_id                 int(11) not null  ,
   tag_type               int(4) not null ,
   source_id              int(11) not null  ,
   status                 tinyint(1) not null default 1,
   order_num              int(4) not null ,
   create_time            datetime,
   modify_time            datetime,
   primary key (work_id,tag_id,source_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;






  /*==============================================================*/
/* Table: work_content_source 作品相关的多媒体资源信息                                         */
/*==============================================================*/
drop table if exists work_content_source;
create table work_content_source
(
   source_id              int(11) not null auto_increment ,
   work_id                int(11) not null,
   source_category        int(4) not null default 0, 
   platform_id            int(4) not null,
   source_type            varchar(100) not null,
   source_val             varchar(200) not null,
   origin_source_id       int(11) not null,
   order_num              int(4) not null default 0, 
   status                 tinyint(1) not null default 1,
   create_time            datetime,
   modify_time            datetime,
   primary key (source_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;
  





/*==============================================================*/
/* Table: work_content_source_extend 作品中多媒体资源的扩展信息表                                              */
/*==============================================================*/
drop table if exists work_content_source_extend;
create table work_content_source_extend
(
   source_id              int(11) not null ,
   extend_attr            int(4) not null ,
   attr_val               varchar(200) not null,
   order_num              int(4) not null ,
   status                 tinyint(1) not null default 1,
   remark                 varchar(200) not null,

   UNIQUE KEY uniq_work_sourceex (source_id,extend_attr,attr_val)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;





  /*==============================================================*/
/* Table: work_source_type_define 作品相关的多媒体资源类型的定义                                        */
/*==============================================================*/
drop table if exists work_source_type_define;
create table work_source_type_define
(
   source_type            varchar(100) not null,
   lv                     tinyint(1) not null default 1, 
   type_name              varchar(100) not null,
   status                 tinyint(1) not null default 1,
   create_time            datetime,
   modify_time            datetime,
   primary key (source_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;
  




  /*==============================================================*/
/* Table: work_role_define 角色定义表                                       */
/*==============================================================*/
drop table if exists work_role_define;
create table work_role_define
(
   role_code            varchar(100) not null,
   lv                     tinyint(1) not null default 1, 
   role_desc              varchar(100) not null,
   status                 tinyint(1) not null default 1,
   primary key (role_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;




  /*==============================================================*/
/* Table: work_attender 作品的相关作者                                       */
/*==============================================================*/
drop table if exists work_attender;
create table work_attender
(
   work_id                int(11) not null  ,
   media_source_id        int(11) not null  ,
   source_category        int(4) not null default 0, 
   role_code              varchar(100) not null,
   author_id              int(11) not null,
   order_num              int(4) not null default 0, 
   status                 tinyint(1) not null default 1,
   primary key (role_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;



  /*==============================================================*/
/* Table: work_hot_index 作品热度表                                     */
/*==============================================================*/
drop table if exists work_hot_index;
create table work_hot_index
(
   work_id                int(11) not null  ,
   score                  int(11) not null  ,
   create_time            datetime,
   modify_time            datetime,
   primary key (work_id),
   KEY idx_work_hotidx_score (score)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;




  /*==============================================================*/
/* Table: work_inspiration 作品创作灵感                                     */
/*==============================================================*/
drop table if exists work_inspiration;
create table work_inspiration
(
   id                     int(11) not null auto_increment  ,
   work_id                  int(11) not null  ,
   author_id               int(11) not null  ,  
   inspiration            varchar(500) not null,
   status                 tinyint(1) not null default 1,
   create_time            datetime,
   modify_time            datetime,
   primary key (id),
   UNIQUE KEY uniq_work_inspri (work_id,author_id)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;






  /*==============================================================*/
/* Table: work_milestone 作品历程碑                                     */
/*==============================================================*/
drop table if exists work_milestone;
create table work_milestone
(
   work_id                  int(11) not null  ,
   mile_attr               int(4) not null  ,  
   status                 tinyint(1) not null default 1,
   mile_time            datetime,
   primary key (work_id,mile_attr)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;






  /*==============================================================*/
/* Table: work_album专辑组成                                     */
/*==============================================================*/
drop table if exists work_album;
create table work_album
(
   album_Id               int(11) not null auto_increment  ,
   work_id               int(11) not null  ,  
   order_num             int(4) not null default 0 ,  
   status                tinyint(1) not null default 1,

   primary key (album_Id,work_id),
    KEY idx_work_album_mem_workid (work_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;








  /*==============================================================*/
/* Table: work_album_member 专辑组成                                     */
/*==============================================================*/
drop table if exists work_album_member;
create table work_album_member
(
   album_Id               int(11) not null ,
   work_id               int(11) not null  ,  
   order_num             int(4) not null default 0 ,  
   status                tinyint(1) not null default 1,

   primary key (album_Id,work_id),
    KEY idx_work_album_mem_workid (work_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;



      <id column="album_Id" property="albumId" jdbcType="BIGINT" />
      <result column="subject" property="subject" jdbcType="VARCHAR" />
      <result column="summary" property="summary" jdbcType="VARCHAR" />
      <result column="cov_pic_url" property="covPicUrl" jdbcType="VARCHAR" />
      <result column="status" property="status" jdbcType="INTEGER" />
      <result column="CREATE_TIME" property="createTime" jdbcType="TIMESTAMP" />
      <result column="MODIFY_TIME" property="modifyTime" jdbcType="TIMESTAMP" />
