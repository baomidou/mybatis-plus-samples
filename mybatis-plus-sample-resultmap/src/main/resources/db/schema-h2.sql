-- noinspection SqlNoDataSourceInspectionForFile

drop table if exists man;
drop table if exists woman;
drop table if exists child;

create table man
(
    id        BIGINT not null,
    name      varchar(30),
    lao_po_id BIGINT not null
);

create table woman
(
    id          BIGINT not null,
    name        varchar(30),
    lao_gong_id BIGINT not null
);

create table child
(
    id         BIGINT not null,
    name       varchar(30),
    lao_han_id BIGINT not null,
    lao_ma_id BIGINT not null
);