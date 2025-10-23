-- 删除数据库
-- DROP DATABASE IF EXISTS mshop;
-- 创建数据库
-- CREATE DATABASE mshop CHARACTER SET UTF8;
-- 使用数据库
-- USE mshop;
-- 删除数据表
drop table if exists shopcar;
drop table if exists details;
drop table if exists orders;
drop table if exists member;
drop table if exists goods;
drop table if exists item;
drop table if exists admin;
-- 创建数据表
create table item
(
    iid int auto_increment,
    title varchar(200) not null,
    constraint pk_iid primary key (iid)
)engine=innodb;
create table admin
(
    aid varchar(50) not null,
    password varchar(32),
    lastdate datetime,
    constraint pk_aid primary key (aid)
)engine=innodb;
create table member
(
    mid varchar(50) not null,
    password varchar(32),
    name varchar(50),
    phone varchar(50),
    address varchar(100),
    status int,
    code varchar(100),
    regdate datetime not null ,
    photo varchar(50) default 'nophoto.jpg',
    constraint pk_mid primary key (mid)
)engine=innodb;
create table goods
(
    gid int auto_increment,
    iid int,
    aid varchar(50),
    title varchar(50),
    pubdate datetime,
    price float,
    amount int,
    bow int,
    note text,
    photo varchar(100),
    status int,
    constraint pk_gid primary key (gid),
    constraint fk_iid foreign key (iid) references item(iid) on delete set null,
    constraint fk_aid foreign key (aid) references admin(aid) on delete set null
)engine=innodb;
create table orders
(
    oid int auto_increment,
    mid varchar(50),
    name varchar(50),
    phone varchar(50),
    address varchar(100),
    credate datetime,
    pay float,
    constraint pk_oid primary key (oid),
    constraint fk_mid foreign key (mid) references member(mid) on delete cascade
)engine=innodb;
create table details
(
    odid int auto_increment,
    oid int not null,
    gid int,
    title varchar(50) not null,
    price float not null,
    amount int not null,
    constraint pk_odid primary key (odid),
    constraint fk_oid foreign key (oid) references orders(oid) on delete cascade,
    constraint fk_gid foreign key (gid) references goods(gid) on delete set null
)engine=innodb;
create table shopcar(
    gid int,
    mid varchar(50),
    amount int,
    constraint fk_gid3 foreign key (gid) references goods(gid) on delete cascade,
    constraint fk_mid3 foreign key (mid) references member(mid) on delete cascade
)engine=innodb;
-- 编写测试数据
-- 增加分类信息
insert into item(title) values('厨房用具');
insert into item(title) values('儿童玩具');
insert into item(title) values('医疗器械');
insert into item(title) values('运动健身');
insert into item(title) values('办公用品');
-- 增加管理员信息
insert into admin(aid,password) values ('admin','5D41402ABC4B2A76B9719D911017C592');
-- 增加普通用户信息
insert into member(mid,password,regdate) values ('mldn','93F725A07423FE1C889F448B33D21F46','1999-9-9');
-- 提交事务
commit;