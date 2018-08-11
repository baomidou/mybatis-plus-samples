-- noinspection SqlNoDataSourceInspectionForFile

DROP TABLE IF EXISTS student;

CREATE TABLE student
(
  id      BIGINT (20) NOT NULL COMMENT '主键ID',
  name    VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
  age     INT (11) NULL DEFAULT NULL COMMENT '年龄',
  PRIMARY KEY (id)
);
