# MP功能集合演示项目

该项目演示以下功能：

1. 逻辑删除
2. 自动填充
3. 自定义全局方法：insert/insertBatch

关于自定义全局方法攻略：

1.定义SQL: 参考DeleteAll, MysqlInsertAllBatch
```java
public class MysqlInsertAllBatch extends AbstractMethod {
    @Override
        public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
            return null;//TODO: 自定义SQL
        }
}
```

2.注册： 参考MyLogicSqlInjector, 注册自定义方法
```java
public class MyLogicSqlInjector extends DefaultSqlInjector {

    /**
     * 如果只需增加方法，保留MP自带方法
     * 可以super.getMethodList() 再add
     * @return
     */
    @Override
    public List<AbstractMethod> getMethodList() {
        List<AbstractMethod> methodList = super.getMethodList();
        methodList.add(new DeleteAll());
        methodList.add(new MyInsertAll());
        methodList.add(new MysqlInsertAllBatch());
        return methodList;
    }
}
```

3.把方法定义到BaseMapper，参考MyBaseMapper
```java
public interface MyBaseMapper<T> extends BaseMapper<T> {
    
    Integer deleteAll();
    
    int myInsertAll(T entity);
    
    int mysqlInsertAllBatch(@Param("list") List<T> batchList);
}
```
> 注意： baseMapper的方法名称必须和自定义全局方法里的id一致

```java
public class MysqlInsertAllBatch extends AbstractMethod {

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        String sql = "xxxxxx";
        //第三个参数必须和baseMapper的自定义方法名一致
        return this.addInsertMappedStatement(mapperClass, modelClass, "mysqlInsertAllBatch", sqlSource, new NoKeyGenerator(), null, null);
    }
```


其中insertBatch: 参考MysqlInsertAllBatch

演示批量保存使用mysql特有语法:

```text
insert into user(id, name, age) values (1, "a", 17), (2,"b", 18)
```

> 坑点：

- 在演示自定义批量和自动填充功能时，需要在mapper方法的参数上定义@Param(), 
- 而mp默认仅支持 list, collection, array 3个命名，不然无法自动填充