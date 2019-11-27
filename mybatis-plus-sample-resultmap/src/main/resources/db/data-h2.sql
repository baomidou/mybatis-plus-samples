-- noinspection SqlNoDataSourceInspectionForFile

insert into man(id, name, lao_po_id)
values (1, '程序猿小明', 1),
       (2, '隔壁老王', 2);

insert into woman(id, name, lao_gong_id)
values (1, '程序猿小明老婆', 1),
       (2, '隔壁老王老婆', 2);

INSERT INTO child (id, name, lao_han_id, lao_ma_id)
VALUES (1, '小小明', 1, 1),
       (2, '小小王', 2, 2),
       (3, '旺仔', 2, 1),
       (4, '小馒头', 2, 1),
       (5, '大礼包', 1, 2);