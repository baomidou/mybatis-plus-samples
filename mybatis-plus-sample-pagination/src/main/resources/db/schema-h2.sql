-- noinspection SqlNoDataSourceInspectionForFile

DROP TABLE IF EXISTS sys_user;

CREATE TABLE sys_user
(
  id      BIGINT NOT NULL COMMENT '主键ID',
  name    VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
  age     INT NULL DEFAULT NULL COMMENT '年龄',
  email   VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS children;

CREATE TABLE children
(
    id      BIGINT NOT NULL COMMENT '主键ID',
    name    VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
    user_id BIGINT NULL DEFAULT NULL COMMENT '上级ID',
    PRIMARY KEY (id)
);