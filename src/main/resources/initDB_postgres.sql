create sequence hibernate_sequence start 1 increment 1

create table cart
(id int8 not null,
user_id int8,
primary key (id))

create table goodcart
(id int8 not null,
 amount int4 not null,
 cost varchar(255),
 title varchar(255),
 uid int8 not null,
 primary key (id))

create table goods
(id int8 not null,
active boolean not null,
category varchar(255),
cost varchar(255),
 description varchar(255),
 filename varchar(255),
 title varchar(255),
 primary key (id))

create table item
(id int8 not null,
goodid int8 not null,
quantity int4 not null,
user_id int8,
item_id int8,
primary key (id))

create table user_role
(user_id int8 not null,
roles varchar(255))

create table usr
(id int8 not null,
activation_code varchar(255),
active boolean not null,
email varchar(255),
password varchar(255),
username varchar(255),
primary key (id))

alter table if exists cart add constraint FKc9objqhvjc84nmsxvwk64dajp foreign key (user_id) references usr
alter table if exists item add constraint FKgheeriae0qdsdl5vdp1gq8ugq foreign key (user_id) references usr
alter table if exists item add constraint FKs2jy3vfqnu3ua2fr5qeroodd2 foreign key (item_id) references cart
alter table if exists user_role add constraint FKfpm8swft53ulq2hl11yplpr5 foreign key (user_id) references usr