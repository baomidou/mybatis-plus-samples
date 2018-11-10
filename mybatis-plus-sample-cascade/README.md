
#### 项目介绍
基于mybatis-plus和mybatis-plus-query的示例项目

#### 使用说明

mybatis-plus-query: https://gitee.com/joker-pper/mybatis-plus-query

mybatis-plus-query是基于mybatis-plus用于增强mapper查询方法的框架,主要用于Wrapper动态条件的生成

默认需要在Mapper类上加入@QuerySupport启用

当mapper查询方法(需要加上@QuerySupport)中存在@Param(Constants.WRAPPER) Wrapper<User> wrapper参数时才会根据所配置的sql语句生成对应的动态sql (所提供的sql支持bind、include,过于复杂的可能不支持)

若需要使用Wrapper的select指定列,对应的sql则需要以 SELECT %s FROM 的方式








