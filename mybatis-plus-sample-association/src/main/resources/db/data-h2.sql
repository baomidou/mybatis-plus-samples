DELETE FROM company;

INSERT INTO company (id, name) VALUES
(1, 'HuaWei'),
(2, 'BYD');


DELETE FROM sys_user;

INSERT INTO sys_user (id, company_id,name, age, email) VALUES
(1, 1, 'Jone', 18, 'test1@baomidou.com'),
(2, 1,'Jack', 20, 'test2@baomidou.com'),
(3, 1, 'Tom', 28, 'test3@baomidou.com'),
(4, 2, 'Sandy', 21, 'test4@baomidou.com'),
(5, 2, 'Billie', 24, 'test5@baomidou.com');