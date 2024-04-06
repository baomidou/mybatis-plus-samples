模拟特定数量的表结构和字段来测试集成mybatis-plus的启动速度.

实测:

机器: Core(TM) i7-12700H  2.70 GHz  64G 

1000张表, 注入1000 * 12(内置方法)  串行注入启动 16秒 左右 
![img.png](img.png)
![img_1.png](img_1.png)
![img_2.png](img_2.png)

1000张表, 注入1000 * 12(内置方法)  并行注入启动 14秒 作用
![img_3.png](img_3.png)
![img_4.png](img_4.png)
![img_5.png](img_5.png)

虚拟机 Core(TM) i7-12700H  2H 4G

1000张表, 注入1000 * 12(内置方法)  串行注入启动 31秒 左右
![img_9.png](img_9.png)
![img_10.png](img_10.png)
![img_11.png](img_11.png)

1000张表, 注入1000 * 12(内置方法)  并行注入启动 30秒 作用
![img_6.png](img_6.png)
![img_7.png](img_7.png)
![img_8.png](img_8.png)

单纯的并行注入方法并不能带来太实际的性能提升，因为本身注入方法数量就少，如果能并行处理Mapper或者异步处理注入看能否提升部分启动速度，但综合来看, 中小型项目串行启动注入不会有太耗时的行为.