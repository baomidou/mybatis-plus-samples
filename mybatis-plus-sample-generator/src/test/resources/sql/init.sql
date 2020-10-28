DROP TABLE IF EXISTS `t_simple`;
CREATE TABLE `t_simple`
(
    id          int AUTO_INCREMENT comment 'id',
    name        varchar(50),
    age         int,
    flag        int,
    version     bigint,
    create_time datetime,
    update_time datetime,
    primary key (id)
);
