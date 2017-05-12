

/*==============================================================*/
/* Table: work_base 作品基本信息表                                              */
/*==============================================================*/
drop table if exists work_base;
create table work_base
(
   work_id                int(11) not null auto_increment ,
   category               int(4) not null ,
   subject                varchar(200) not null,
   summary                varchar(500) not null,

   work_tags_desc         varchar(200) not null,
   work_tags_ids          varchar(200) not null,
   status                 tinyint(1) not null default 1,


   editors_desc           varchar(200) not null,
   editors_ids            varchar(200) not null,

   producters_desc        varchar(200) not null,
   producters_ids         varchar(200) not null,

   fav_num                int(11) not null default 0,
   comment_num            int(11) not null default 0,
   transpond_num          int(11) not null default 0,
   upvote_num             int(11) not null default 0,
   read_num               int(11) not null default 0,
  

   create_time            datetime,
   modify_time            datetime,

   primary key (work_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;





 /*==============================================================*/
/* Table: work_tag_mapping 作品tag标签映射                                              */
/*==============================================================*/
drop table if exists work_tag_mapping;
create table work_tag_mapping
(
   work_id                int(11) not null  ,
   tag_id                 bigint(20) not null ,
   tag_type               int(4) not null ,
   status                 tinyint(1) not null default 1,
   order_num              int(4) not null ,
   create_time            datetime,
   modify_time            datetime,
   primary key (work_id,tag_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;




 /*==============================================================*/
/* Table: work_attr_mapping 作品扩展属性映射表                                            */
/*==============================================================*/
drop table if exists work_attr_mapping;
create table work_attr_mapping
(
   work_id                int(11) not null  ,
   attr_type              int(4) not null ,
   value                  varchar(200) not null,
   order_num              int(4) not null,
   status                 tinyint(1) not null default 1,
   create_time            datetime,
   modify_time            datetime,
   KEY idx_work_attr_map_id_and_attr (work_id,attr_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;

   


  /*==============================================================*/
/* Table: work_media_source 作品相关的多媒体资源信息                                         */
/*==============================================================*/
drop table if exists work_media_source;
create table work_media_source
(
   source_id              int(11) not null auto_increment ,
   work_id                int(11) not null,
   source_channel         int(4) not null default 0, 
   category               tinyint(1) not null default 0, 
   source_type            int(4) not null default 0, 
   source_val             varchar(500) not null,
   status                 tinyint(1) not null default 1,
   create_time            datetime,
   modify_time            datetime,
   primary key (source_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;
  









