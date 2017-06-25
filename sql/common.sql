



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
  









      <result column="batch_num" property="batchNum" jdbcType="INTEGER" />
      <result column="source_type" property="sourceType" jdbcType="VARCHAR" />      
      <result column="status" property="status" jdbcType="INTEGER" />
      <result column="CREATE_TIME" property="createTime" jdbcType="TIMESTAMP" />
      <result column="MODIFY_TIME" property="modifyTime" jdbcType="TIMESTAMP" />