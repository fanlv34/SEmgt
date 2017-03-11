drop table if exists User;

/*==============================================================*/
/* Table: User                                                  */
/*==============================================================*/
create table User
(
   userId               int not null auto_increment,
   username             varchar(20) not null,
   password             varchar(40) not null,
   email                varchar(50),
   mobile               varchar(11),
   regDate              datetime,
   lastLoginTime        datetime,
   primary key (userId)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


drop table if exists Series;

/*==============================================================*/
/* Table: Series                                                */
/*==============================================================*/
create table Series
(
   seriesId             int not null auto_increment,
   userId               int not null,
   seriesNameCN         varchar(50),
   seriesNickName       varchar(80),
   seriesNameOrg        varchar(50),
   headSeason           tinyint not null default 1,
   currentSeason        tinyint not null,
   episode              int not null default 0,
   comingDate           varchar(8),
   fuzzyDate            char default 'C' comment 'C - 清晰时间
            Y - 模糊到年
            M - 模糊到月',
   updateWeekday        tinyint,
   isEnd                char not null comment 'B - 在播
            S - 本季完结
            E - 剧终
            N - 未开播',
   isAbandoned          tinyint not null default 0 comment '0 - 想看
            1 - 在追
            2 - 半弃
            3 - 已弃',
   jnlDate              datetime not null,
   priority             tinyint default 0,
   urlType              varchar(5),
   customUrl            varchar(2048),
   rating               tinyint default 0,
   primary key (seriesId, userId)
);

alter table Series add constraint FK_Reference_1 foreign key (userId)
      references User (userId) on delete restrict on update restrict;


drop table if exists Rule;

/*==============================================================*/
/* Table: Rule                                                  */
/*==============================================================*/
create table Rule
(
   ruleType             varchar(16) not null,
   ruleId               varchar(64) not null,
   userType             char(1) not null,
   ruleDef              varchar(1024),
   ruleScript           varchar(2056),
   primary key (ruleType, ruleId, userType)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table Rule comment '设置系统级参数规则，公共规则定义';

insert into rule values('downloadUrl','du000','0','自定义|自定义链接|',null);
insert into Rule values('downloadUrl','du001','0','磁力链|cili17.com|/?topic_title3=',null);
insert into Rule values('downloadUrl','du002','0','字幕组|www.zmz2017.com|/search?keyword=',null);

--update Rule set ruledef = '磁力链|cili15.com|/?topic_title3=' where ruleType='downloadUrl' and ruleId='du001';