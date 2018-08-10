-- noinspection SqlNoDataSourceInspectionForFile

DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS role;

CREATE TABLE user
(
  id      BIGINT (20) NOT NULL COMMENT '主键ID',
  name    VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
  age     INT (11) NULL DEFAULT NULL COMMENT '年龄',
  email   VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
  role_id BIGINT (20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (id)
);

CREATE TABLE role
(
  id            BIGINT (20) NOT NULL COMMENT '主键ID',
  role_name     VARCHAR(30) NULL DEFAULT NULL COMMENT '角色名',
  role_describe VARCHAR(30) NULL DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY (id)
);