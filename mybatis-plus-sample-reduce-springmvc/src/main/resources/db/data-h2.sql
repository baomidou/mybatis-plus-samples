DELETE FROM sys_user;

INSERT INTO sys_user (id, name, age, email) VALUES
(1, 'Jone', 18, 'test1@baomidou.com'),
(2, 'Jack', 20, 'test2@baomidou.com'),
(3, 'Tom', 28, 'test3@baomidou.com'),
(4, 'Sandy', 21, 'test4@baomidou.com'),
(5, 'Billie', 24, 'test5@baomidou.com');

DELETE FROM district;
INSERT INTO district(id, name, city, district) VALUES
(1, '南山', '深圳', '南山区'),
(2, '福田', '深圳', '福田区'),
(3, '罗湖', '深圳', '罗湖区');


DELETE FROM city;
INSERT INTO city(id, name, province) VALUES
(1,  '广州', '广东'),
(2,  '深圳', '广东'),
(3,  '佛山', '广东');
