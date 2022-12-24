DELETE FROM sys_user;

INSERT INTO sys_user (id, name, age, email, grade, gender, user_state, str_enum)
VALUES (2, 'Jack', 3, 'test2@baomidou.com', 1, 0, 1, 'one'),
       (3, 'Tom', 1, 'test3@baomidou.com', 2, 1, 1, 'one'),
       (1, 'Billie', 2, 'test5@baomidou.com', 3, null, 1, 'two');