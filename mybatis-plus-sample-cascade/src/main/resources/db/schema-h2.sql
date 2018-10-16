-- noinspection SqlNoDataSourceInspectionForFile

DROP TABLE IF EXISTS user;

CREATE TABLE user
(
  id      BIGINT (20) NOT NULL COMMENT '主键ID',
  username    VARCHAR(30) NULL DEFAULT NULL COMMENT '用户名',
  password     VARCHAR (100) NULL DEFAULT NULL COMMENT '密码',
  create_time   TIMESTAMP  NULL DEFAULT NULL COMMENT '创建时间',
  role_id BIGINT(30) NULL DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS role;
CREATE TABLE role
(
  id      BIGINT (20) NOT NULL COMMENT '主键ID',
  name    VARCHAR(30) NULL DEFAULT NULL COMMENT '名称',
  create_time   TIMESTAMP  NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (id)
);
