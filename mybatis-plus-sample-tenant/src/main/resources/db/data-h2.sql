DELETE FROM sys_user;

INSERT INTO sys_user (id, tenant_id, name) VALUES
(1, 1, 'Jone'),(2, 1, 'Jack'),(3, 1, 'Tom'),
(4, 0, 'Sandy'),(5, 0, 'Billie');

INSERT INTO user_addr (id, USER_ID, name) VALUES
(1, 1, 'addr1'),(2,1,'addr2');