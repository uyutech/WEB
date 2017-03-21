


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
/* Table: ���ʱ䶯���α�                                           */
/*==============================================================*/
drop table if exists T_EXCHANGE_BATCH_RECORD;

create table T_EXCHANGE_BATCH_RECORD
(
   BATCH_ID              int(11) not null auto_increment comment '����ID��',
   CURRENT_EXCHANGE      double(10,4) not null comment '��ǰ����ֵ',
   TARGET_EXCHANGE       double(10,4) not null comment 'Ŀ�����ֵ',
   STATUS                tinyint(1) not null default 0 comment '״̬ 0-����  1-��Ч',
   SECTION_INFO          text not null comment '������ϸ��Ϣ',
   CREATOR               int(11) not null comment '������',
   CREATE_TIME           datetime not null comment '����ʱ��',
   primary key (BATCH_ID),
   KEY batch_record_idx_time (CREATE_TIME)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT '���ʱ䶯���α�';














/*==============================================================*/
/* Table: ���ʽڵ������                                         */
/*==============================================================*/

drop table if exists T_EXCHANGE_NODE;

create table T_EXCHANGE_NODE
(
   ID                   int(11) not null auto_increment comment 'ID��',
   BATCH_NUM            int(11) not null comment '���κ�',
   VALUE                double(10,3) not null comment '����ֵ',
   SYNC_TIME            datetime not null comment '����ͬ��ʱ��',
   NODE_STATUS          tinyint(1) not null  comment '�ڵ�״̬ 0-��Ч 1-��ͬ�� 2-ͬ��ʧ�ܵȴ����� 3-ͬ��ʧ�ܷ��� 4-ͬ���ɹ�',
   RETRY_COUNT          tinyint(1) not null comment '���Դ���',
   CREATOR              int(11) not null comment '������',
   CREATE_TIME          datetime not null comment '����ʱ��',
   MODIFIER             int(11) comment '������',
   MODIFY_TIME          datetime comment '�޸�ʱ��',
   primary key (ID),
   KEY node_idx_batchnum (BATCH_NUM),
   KEY node_idx_synctime (SYNC_TIME)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT '���ʽڵ������';














/*==============================================================*/
/* Table: ������־��                                            */
/*==============================================================*/

drop table if exists t_oper_log;

create table t_oper_log
(
   ID                   int(11) not null auto_increment comment 'ID��',
   BIZ_TYPE             int(3) not null comment 'ҵ������',
   OPER_TYPE            int(3) not null comment '��������',
   RESULT               tinyint(1) not null comment '���������',
   OPER_ACCOUNT         varchar(32) comment '�û�����',
   CONTENT              text comment '��־����',
   CREATE_TIME          datetime not null comment '����ʱ��',
   primary key (ID),
   KEY oper_log_idx_biztype_time (BIZ_TYPE,CREATE_TIME),
   KEY oper_log_idx_time (CREATE_TIME),
   KEY oper_log_account_time (OPER_ACCOUNT,CREATE_TIME)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT '������־';








/*==============================================================*/
/* Table: ϵͳ�Բ�����־                                        */
/*==============================================================*/

drop table if exists t_sync_log;

create table t_sync_log
(
   ID                   int(11) not null auto_increment comment 'ID��',
   OPER_TYPE            int(3) not null comment '��������',
   SYSTEM_CODE          int(3) not null comment 'ϵͳ���� 0-ģ���� 1-ʵ�� 2-΢��',
   content              text comment 'ͬ������',
   result               tinyint(1) not null comment '���������0-�ɹ� 1ʧ��',
   CREATE_TIME          datetime not null comment '����ʱ��',
   primary key (ID),
   KEY sync_log_idx_time (SYSTEM_CODE,CREATE_TIME)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT 'ϵͳͬ����¼';










/*==============================================================*/
/* Table: ��Ʒ������Ҫ���л���ͬ��ʱ���漰������Ʒ��Ϣ        */
/*==============================================================*/
drop table if exists T_COMMODITY;
create table T_COMMODITY
(
   CODE                 varchar(32) not null comment '��Ʒ����',
   NAME                 varchar(100) not null comment '��Ʒ����',
   STATUS               tinyint(1) not null default 0 comment '״̬ 0-����  1-��Ч',
   CREATOR              bigint(20) not null comment '������',
   CREATE_TIME          datetime not null comment '��������',
   MODIFIER             int(11) comment '�޸���',
   MODIFY_TIME          datetime comment '�޸�ʱ��',
   primary key (CODE)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '��Ʒ��';











/*==============================================================*/
/* Table: �����ֵ��                                                */
/*==============================================================*/
drop table if exists T_DATA_DICT;

create table T_DATA_DICT
(
   ID                   int(11) not null auto_increment comment 'ID��',
   DICT_CODE            varchar(32) not null comment '�ֵ����',
   DICT_NAME            varchar(128) not null comment '�ֵ�����',
   DICT_VALUE           varchar(128) not null comment '�ֵ�ֵ',
   DICT_VALUE_TYPE      tinyint(1) not null comment 'ֵ����������',  
   DICT_DESC            varchar(512) comment '����',
   STATUS               tinyint(1) not null default 0 comment '״̬ 0-����  1-��Ч',
   CREATOR               int(11) not null comment '������',
   CREATE_TIME           datetime not null comment '����ʱ��',
   MODIFIER              int(11) comment '������',
   MODIFY_TIME           datetime comment '����ʱ��',
   primary key (ID),
   UNIQUE KEY uniq_datadict_code (DICT_CODE), 
   KEY datadict_idx_name (DICT_NAME)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT '�ֵ��';


insert into t_data_dict(dict_code,dict_name,dict_value,dict_value_type,dict_desc,status,creator,create_time)
values('MIN_MODIFY_INTERVAL_TIME','�����ֵ��������С���ʱ��(����)',5,1,'����ĳ���ڵ����ֵʱ��������ʱ��ӦС�����Ļ���ͬ��ʱ�䣬��Ӧ��һ���ļ��ʱ�䣬��ֵ����С�ڵ���0�����ʾ���жϣ���λ:����',0,-1,now());

insert into t_data_dict(dict_code,dict_name,dict_value,dict_value_type,dict_desc,status,creator,create_time)
values('EXCHANGE_MAX_NODES','���ʸ������䶯�ڵ���',8,1,'��������ʱ����ȡ����ڵ���������',0,-1,now());

insert into t_data_dict(dict_code,dict_name,dict_value,dict_value_type,dict_desc,status,creator,create_time)
values('EXCHANGE_MIN_NODES','���ʸ�����С�䶯�ڵ���',5,1,'��������ʱ����ȡ����ڵ���������',0,-1,now());

insert into t_data_dict(dict_code,dict_name,dict_value,dict_value_type,dict_desc,status,creator,create_time)
values('EXCHANGE_MAX_TIME_DURATION','���ʱ䶯���ı䶯����(��λ:Сʱ)',10,1,'��������ʱ����ȡ����䶯����ʱ��������',0,-1,now());

insert into t_data_dict(dict_code,dict_name,dict_value,dict_value_type,dict_desc,status,creator,create_time)
values('EXCHANGE_MIN_TIME_DURATION','���ʱ䶯��С�ı䶯����(��λ:Сʱ)',1,1,'��������ʱ����ȡ����䶯����ʱ��������',0,-1,now());

insert into t_data_dict(dict_code,dict_name,dict_value,dict_value_type,dict_desc,status,creator,create_time)
values('EX_CHANGE_BIG_LIMIT','���ʱ䶯��������(�ٷֱ�)',0.75,3,'���ʱ䶯���������趨���޶�ʱ�����Զ����ɻ���ͬ������������0.75����ʾ�䶯��������0.75%ϵͳ�;ܾ����ɽڵ㡣',0,-1,now());


insert into t_data_dict(dict_code,dict_name,dict_value,dict_value_type,dict_desc,status,creator,create_time)
values('IS_SYNC_TO_MOCK_SYSTEM','�Ƿ�ͬ����ģ����',1,1,'�Ƿ񽫻���ͬ����ģ����(0-��ͬ�� 1-ͬ��)',0,-1,now());


insert into t_data_dict(dict_code,dict_name,dict_value,dict_value_type,dict_desc,status,creator,create_time)
values('IS_SYNC_TO_ACTUAL_SYSTEM','�Ƿ�ͬ����ʵ��',1,1,'�Ƿ񽫻���ͬ����ʵ��(0-��ͬ�� 1-ͬ��)',0,-1,now());


insert into t_data_dict(dict_code,dict_name,dict_value,dict_value_type,dict_desc,status,creator,create_time)
values('IS_SYNC_TO_MICRO_SYSTEM','�Ƿ�ͬ����΢��',1,1,'�Ƿ񽫻���ͬ����΢��(0-��ͬ�� 1-ͬ��)',0,-1,now());









