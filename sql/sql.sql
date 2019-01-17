
create database spider  CHARACTER set 'utf8'
use spider;


create table if not exists website
(
   id                   int not null auto_increment primary key,
   name                 varchar(20) not null,
   url                  varchar(255) not null,
   assortment           int,
   domain               varchar(100),
   userAgent            varchar(255),
   saveDir              varchar(100),
   cookie               varchar(1000),
   referer              varchar(255),
   listUrlRegex         varchar(255) not null,
   listXPath            varchar(255) not null,
   titleXPath           varchar(255) not null,
   contentUrlRegex      varchar(255),
   contentXPath         varchar(255) not null,
   abstractXPath        varchar(255),
   status               int not null default 0,
   dateXPath            varchar(255),
   dateRegex            varchar(50),
   sleepTime            int
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table if not exists zdata
(
id int PRIMARY key auto_increment,
  question VARCHAR(255) COMMENT '问题名称',
questionDescription VARCHAR(255) COMMENT '问题描述',
answers VARCHAR(255) COMMENT '回答'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SELECT * from zdata;

show variables like 'character%';

