DROP TABLE IF EXISTS common;
DROP TABLE IF EXISTS null1;
DROP TABLE IF EXISTS null2;

CREATE TABLE common
(
    id      BIGINT(20) NOT NULL,
    name    VARCHAR(20) NOT NULL,
    deleted INT(11) NOT NULL DEFAULT 0,
    PRIMARY KEY (id)
);

CREATE TABLE null1
(
    id      BIGINT(20) NOT NULL,
    name    VARCHAR(20) NOT NULL,
    deleted INT(11),
    PRIMARY KEY (id)
);

CREATE TABLE null2
(
    id       BIGINT(20) NOT NULL,
    name     VARCHAR(20) NOT NULL,
    del_time TIMESTAMP,
    PRIMARY KEY (id)
);