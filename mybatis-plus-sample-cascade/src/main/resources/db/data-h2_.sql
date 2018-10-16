
DELETE FROM user;
DELETE FROM role;

INSERT INTO user (id, username, password, role_id, create_time)
VALUES
(1, 'joker', 'sadasd', 1, {ts '2018-10-10 14:59:43.69'}),
(2, 'yyc', 'sadasd', 1, {ts' 2018-10-10 15:23:13.69'}),
(3, 'zhangsan', 'sadasd', 2, {ts '2018-10-10 15:21:23.69'});


INSERT INTO role (id, name, create_time)
VALUES
(1, 'admin', {ts '2018-10-10 14:59:43.69'}),
(2, 'sa', {ts '2018-10-10 18:23:13.69'})
