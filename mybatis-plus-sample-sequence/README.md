# 主键Sequence

针对有序列的数据库：比如Oracle，SQLServer等

让我们以Oracle举例：
我们可以通过以下几个简单步骤，就可以完成Mybatis-Plus主键Sequence的定义

## 1.定义`IKeyGenerator`的实现
Mybatis-Plus已经定义好了常见的数据库主键序列类：
比如：`OracleKeyGenerator`

> 如果你使用了`mybatis-plus-boot-starter`, 那么我们只需要在@Configuration类中定义好@Bean
```java
    @Bean
    public OracleKeyGenerator oracleKeyGenerator(){
        return new OracleKeyGenerator();
    }
```

> 如果没有使用`mybatis-plus-boot-starter`, 那么需要手动注入：

GlobalConfiguration配置KeyGenerator

```java
  GlobalConfiguration gc = new GlobalConfiguration();
  gc.setKeyGenerator(new OracleKeyGenerator());
```

## 2.配置KeySequence注解

实体类配置主键Sequence,指定主键@TableId(type=IdType.INPUT)//不能使用AUTO

```java
@TableName("TEST_SEQUSER")
@KeySequence("SEQ_TEST")//类注解
public class TestSequser{
  @TableId(value = "ID", type = IdType.INPUT)
  private Long id;

}
```

* 支持父类定义@KeySequence, 子类使用，这样就可以几个表共用一个Sequence

> 如果主键是String类型的，也可以使用

如何使用Sequence作为主键，但是实体主键类型是String
也就是说，表的主键是varchar2, 但是需要从sequence中取值

* 1.实体定义@KeySequence 注解clazz指定类型String.class
* 2.实体定义主键的类型String
* 3.注意：oracle的sequence返回的是Long类型，如果主键类型是Integer，可能会引起ClassCastException
```java
@KeySequence(value = "SEQ_ORACLE_STRING_KEY", clazz = String.class)
public class YourEntity{
    
    @TableId(value = "ID_STR", type = IdType.INPUT)
    private String idStr;
    ...
}
```


## 底层实现

mybatis原生提供了接口

先看下`com.baomidou.mybatisplus.core.metadata.TableInfoHelper`这个类：

```java
    /**
     * 自定义 KEY 生成器
     */
    public static KeyGenerator genKeyGenerator(TableInfo tableInfo, MapperBuilderAssistant builderAssistant,
                                               String baseStatementId, LanguageDriver languageDriver) {
        IKeyGenerator keyGenerator = GlobalConfigUtils.getKeyGenerator(builderAssistant.getConfiguration());
        if (null == keyGenerator) {
            throw new IllegalArgumentException("not configure IKeyGenerator implementation class.");
        }
        String id = baseStatementId + SelectKeyGenerator.SELECT_KEY_SUFFIX;
        Class<?> resultTypeClass = tableInfo.getKeySequence().clazz();
        StatementType statementType = StatementType.PREPARED;
        String keyProperty = tableInfo.getKeyProperty();
        String keyColumn = tableInfo.getKeyColumn();
        SqlSource sqlSource = languageDriver.createSqlSource(builderAssistant.getConfiguration(),
            keyGenerator.executeSql(tableInfo.getKeySequence().value()), null);
        builderAssistant.addMappedStatement(id, sqlSource, statementType, SqlCommandType.SELECT, null, null, null,
            null, null, resultTypeClass, null, false, false, false,
            new NoKeyGenerator(), keyProperty, keyColumn, null, languageDriver, null);
        id = builderAssistant.applyCurrentNamespace(id, false);
        MappedStatement keyStatement = builderAssistant.getConfiguration().getMappedStatement(id, false);
        SelectKeyGenerator selectKeyGenerator = new SelectKeyGenerator(keyStatement, true);
        builderAssistant.getConfiguration().addKeyGenerator(id, selectKeyGenerator);
        return selectKeyGenerator;    
    }
```

1. 通过反射获取到实体的KeySequence注解，继而能拿到定义的Sequence序列名
2. 然后产生`SelectKeyGenerator`对象，这个对象是mybatis原生的主键生成器
3. 将`SelectKeyGenerator`绑定到insert/insertBatch的statement上面
4. 通过上述3步，就完成了自动注入主键生成的SQL

> `SelectKeyGenerator`对象是mybatis原生的主键生成器, 会在：执行sql之前调用主键生成器的方法获取主键结果，插入到insert语句中；
在执行成功之后把主键的值回塞到实体对象中，这样我们只需通过对象的getId()方法就可以获取到主键值