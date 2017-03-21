


/*==============================================================*/
/* Table: user profile                                     */
/*==============================================================*/
drop table if exists user_profile;
create table user_profile
(
   uid                     int(11) not null auto_increment ,
   user_name               varchar(50) not null ,
   nick_name              varchar(50) not null ,
   password               varchar(64) not null ,
   headUrl                varchar(100) not null ,
   gender                 tinyint(1) not null default 0 ,
   allow_attation         tinyint(1) not null default 0 ,
   status                 tinyint(1) not null default 0 ,
   create_time            datetime not null,
   modify_Time             datetime not null,
   primary key (uid),
   UNIQUE KEY uniq_up_account (user_name), 
   UNIQUE KEY uniq_up_nick (nick_name), 
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 ;


insert into T_USER (name,account,password,status)
values('admin','admin','5CC00F6A96B2F7BE346077D1B9E330129501A1B831D33A3D132D75B1F9829465',0);



        <result column="password" property="password" jdbcType="VARCHAR" />
        <result column="status" property="status" jdbcType="INTEGER" />

        <result column="headUrl" property="headUrl" jdbcType="VARCHAR" />


        <result column="gender" property="gender" jdbcType="INTEGER" />

        <result column="allow_attation" property="allowAttation" jdbcType="INTEGER" />


        <result column="CREATE_TIME" property="createTime" jdbcType="TIMESTAMP" />
        <result column="MODIFY_TIME" property="modifyTime" jdbcType="TIMESTAMP" />






/*==============================================================*/
/* Table: 汇率变动批次表                                           */
/*==============================================================*/
drop table if exists T_EXCHANGE_BATCH_RECORD;

create table T_EXCHANGE_BATCH_RECORD
(
   BATCH_ID              int(11) not null auto_increment comment '批次ID号',
   CURRENT_EXCHANGE      double(10,4) not null comment '当前汇率值',
   TARGET_EXCHANGE       double(10,4) not null comment '目标汇率值',
   STATUS                tinyint(1) not null default 0 comment '状态 0-正常  1-无效',
   SECTION_INFO          text not null comment '区段详细信息',
   CREATOR               int(11) not null comment '创建人',
   CREATE_TIME           datetime not null comment '创建时间',
   primary key (BATCH_ID),
   KEY batch_record_idx_time (CREATE_TIME)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT '汇率变动批次表';














/*==============================================================*/
/* Table: 汇率节点详情表                                         */
/*==============================================================*/

drop table if exists T_EXCHANGE_NODE;

create table T_EXCHANGE_NODE
(
   ID                   int(11) not null auto_increment comment 'ID号',
   BATCH_NUM            int(11) not null comment '批次号',
   VALUE                double(10,3) not null comment '汇率值',
   SYNC_TIME            datetime not null comment '汇率同步时间',
   NODE_STATUS          tinyint(1) not null  comment '节点状态 0-无效 1-待同步 2-同步失败等待重试 3-同步失败放弃 4-同步成功',
   RETRY_COUNT          tinyint(1) not null comment '重试次数',
   CREATOR              int(11) not null comment '创建人',
   CREATE_TIME          datetime not null comment '创建时间',
   MODIFIER             int(11) comment '更改人',
   MODIFY_TIME          datetime comment '修改时间',
   primary key (ID),
   KEY node_idx_batchnum (BATCH_NUM),
   KEY node_idx_synctime (SYNC_TIME)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT '汇率节点详情表';














/*==============================================================*/
/* Table: 操作日志表                                            */
/*==============================================================*/

drop table if exists t_oper_log;

create table t_oper_log
(
   ID                   int(11) not null auto_increment comment 'ID号',
   BIZ_TYPE             int(3) not null comment '业务类型',
   OPER_TYPE            int(3) not null comment '操作类型',
   RESULT               tinyint(1) not null comment '操作结果：',
   OPER_ACCOUNT         varchar(32) comment '用户姓名',
   CONTENT              text comment '日志描述',
   CREATE_TIME          datetime not null comment '创建时间',
   primary key (ID),
   KEY oper_log_idx_biztype_time (BIZ_TYPE,CREATE_TIME),
   KEY oper_log_idx_time (CREATE_TIME),
   KEY oper_log_account_time (OPER_ACCOUNT,CREATE_TIME)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT '操作日志';








/*==============================================================*/
/* Table: 系统自操作日志                                        */
/*==============================================================*/

drop table if exists t_sync_log;

create table t_sync_log
(
   ID                   int(11) not null auto_increment comment 'ID号',
   OPER_TYPE            int(3) not null comment '操作类型',
   SYSTEM_CODE          int(3) not null comment '系统编码 0-模拟盘 1-实盘 2-微盘',
   content              text comment '同步内容',
   result               tinyint(1) not null comment '操作结果：0-成功 1失败',
   CREATE_TIME          datetime not null comment '创建时间',
   primary key (ID),
   KEY sync_log_idx_time (SYSTEM_CODE,CREATE_TIME)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT '系统同步记录';










/*==============================================================*/
/* Table: 商品表，即需要进行汇率同步时，涉及到的商品信息        */
/*==============================================================*/
drop table if exists T_COMMODITY;
create table T_COMMODITY
(
   CODE                 varchar(32) not null comment '商品代码',
   NAME                 varchar(100) not null comment '商品名称',
   STATUS               tinyint(1) not null default 0 comment '状态 0-正常  1-无效',
   CREATOR              bigint(20) not null comment '创建人',
   CREATE_TIME          datetime not null comment '创建日期',
   MODIFIER             int(11) comment '修改人',
   MODIFY_TIME          datetime comment '修改时间',
   primary key (CODE)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '商品表';











/*==============================================================*/
/* Table: 数据字典表                                                */
/*==============================================================*/
drop table if exists T_DATA_DICT;

create table T_DATA_DICT
(
   ID                   int(11) not null auto_increment comment 'ID号',
   DICT_CODE            varchar(32) not null comment '字典编码',
   DICT_NAME            varchar(128) not null comment '字典名称',
   DICT_VALUE           varchar(128) not null comment '字典值',
   DICT_VALUE_TYPE      tinyint(1) not null comment '值的数据类型',  
   DICT_DESC            varchar(512) comment '描述',
   STATUS               tinyint(1) not null default 0 comment '状态 0-正常  1-无效',
   CREATOR               int(11) not null comment '创建人',
   CREATE_TIME           datetime not null comment '创建时间',
   MODIFIER              int(11) comment '更改人',
   MODIFY_TIME           datetime comment '更改时间',
   primary key (ID),
   UNIQUE KEY uniq_datadict_code (DICT_CODE), 
   KEY datadict_idx_name (DICT_NAME)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT '字典表';


insert into t_data_dict(dict_code,dict_name,dict_value,dict_value_type,dict_desc,status,creator,create_time)
values('MIN_MODIFY_INTERVAL_TIME','点汇率值调整的最小间隔时间(分钟)',5,1,'调整某个节点汇率值时，服务器时间应小于它的汇率同步时间，并应有一定的间隔时间，该值设置小于等于0，则表示不判断，单位:分钟',0,-1,now());

insert into t_data_dict(dict_code,dict_name,dict_value,dict_value_type,dict_desc,status,creator,create_time)
values('EXCHANGE_MAX_NODES','汇率浮动最大变动节点数',8,1,'汇率生成时，获取随机节点数的上限',0,-1,now());

insert into t_data_dict(dict_code,dict_name,dict_value,dict_value_type,dict_desc,status,creator,create_time)
values('EXCHANGE_MIN_NODES','汇率浮动最小变动节点数',5,1,'汇率生成时，获取随机节点数的下限',0,-1,now());

insert into t_data_dict(dict_code,dict_name,dict_value,dict_value_type,dict_desc,status,creator,create_time)
values('EXCHANGE_MAX_TIME_DURATION','汇率变动最大的变动周期(单位:小时)',10,1,'汇率生成时，获取随机变动周期时长的上限',0,-1,now());

insert into t_data_dict(dict_code,dict_name,dict_value,dict_value_type,dict_desc,status,creator,create_time)
values('EXCHANGE_MIN_TIME_DURATION','汇率变动最小的变动周期(单位:小时)',1,1,'汇率生成时，获取随机变动周期时长的下限',0,-1,now());

insert into t_data_dict(dict_code,dict_name,dict_value,dict_value_type,dict_desc,status,creator,create_time)
values('EX_CHANGE_BIG_LIMIT','汇率变动比例上限(百分比)',0.75,3,'汇率变动比例超过设定的限额时，不自动生成汇率同步。比如设置0.75，表示变动比例超过0.75%系统就拒绝生成节点。',0,-1,now());


insert into t_data_dict(dict_code,dict_name,dict_value,dict_value_type,dict_desc,status,creator,create_time)
values('IS_SYNC_TO_MOCK_SYSTEM','是否同步到模拟盘',1,1,'是否将汇率同步到模拟盘(0-不同步 1-同步)',0,-1,now());


insert into t_data_dict(dict_code,dict_name,dict_value,dict_value_type,dict_desc,status,creator,create_time)
values('IS_SYNC_TO_ACTUAL_SYSTEM','是否同步到实盘',1,1,'是否将汇率同步到实盘(0-不同步 1-同步)',0,-1,now());


insert into t_data_dict(dict_code,dict_name,dict_value,dict_value_type,dict_desc,status,creator,create_time)
values('IS_SYNC_TO_MICRO_SYSTEM','是否同步到微盘',1,1,'是否将汇率同步到微盘(0-不同步 1-同步)',0,-1,now());









