-- noinspection SqlNoDataSourceInspectionForFile

DROP TABLE IF EXISTS student;

CREATE TABLE student
(
  id      BIGINT NOT NULL COMMENT '主键ID',
  name    VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
  age     INT NULL DEFAULT NULL COMMENT '年龄',
  PRIMARY KEY (id)
);
