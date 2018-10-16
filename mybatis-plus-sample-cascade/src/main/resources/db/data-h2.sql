
DELETE FROM user;

INSERT INTO user (id, username, password, role_id, create_time)
VALUES
       (1, 'joker', 'sadasd', 1, parsedatetime('2018-10-10 13:42:52.69', 'yyyy-MM-dd hh:mm:ss.SS')),
       (2, 'yyc', 'sadasd', 1, parsedatetime('2018-10-10 14:47:52.69', 'yyyy-MM-dd hh:mm:ss.SS')),
       (3, 'zhangsan', 'sadasd', 2, parsedatetime('2018-10-10 16:23:52.69', 'yyyy-MM-dd hh:mm:ss.SS')),
       (4, 'wangwu', 'sadasd', 2, parsedatetime('2018-10-10 18:18:10.15', 'yyyy-MM-dd hh:mm:ss.SS'));

INSERT INTO role (id, name, create_time)
VALUES
  (1, 'admin',  parsedatetime('2018-10-08 18:18:10.15', 'yyyy-MM-dd hh:mm:ss.SS')),
  (2, 'sa',  parsedatetime('2018-10-10 18:18:10.15', 'yyyy-MM-dd hh:mm:ss.SS'));

