-- noinspection SqlNoDataSourceInspectionForFile

drop table if exists man;
drop table if exists woman;
drop table if exists child;

create table man
(
    id        bigint(20) not null,
    name      varchar(30),
    lao_po_id bigint(20) not null
);

create table woman
(
    id          bigint(20) not null,
    name        varchar(30),
    lao_gong_id bigint(20) not null
);

create table child
(
    id         bigint(20) not null,
    name       varchar(30),
    lao_han_id bigint(20) not null,
    lao_ma_id bigint(20) not null
);