DROP TABLE IF EXISTS sys_user;

CREATE TABLE sys_user
(
    id         BIGINT NOT NULL COMMENT '主键ID',
    name       VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
    age        INT NULL DEFAULT NULL COMMENT '年龄',
    gender     INT NULL DEFAULT NULL COMMENT '性别,0:MALE, 1:FEMALE',
    grade      INT NULL DEFAULT NULL COMMENT '年级',
    email      VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
    user_state INT NULL DEFAULT NULL COMMENT '用户状态',
    str_enum   VARCHAR(50) NULL,
    PRIMARY KEY (id)
);