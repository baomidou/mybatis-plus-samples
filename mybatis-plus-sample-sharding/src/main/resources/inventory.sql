CREATE TABLE IF NOT EXISTS t_user (id BIGINT PRIMARY KEY, name VARCHAR(64));
MERGE INTO t_user KEY(id) VALUES (1, 'inventory-group');
