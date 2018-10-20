package com.devloper.joker.mybatisplus.cascade.config.query;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.TextSqlNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * 用于支持自定义Wrapper查询
 */
@Component
public class QuerySupportMethod extends AbstractMethod {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected final Map<Class, Map<String, Method>> supportMethodMap = new HashMap<>(16);

    protected final List<QuerySupportProperty> propertyList = new ArrayList<>(16);

    public class QuerySupportProperty {
        private Class<?> mapperClass;
        private Class<?> modelClass;
        private TableInfo tableInfo;

        public QuerySupportProperty() {
        }

        public QuerySupportProperty(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
            this.mapperClass = mapperClass;
            this.modelClass = modelClass;
            this.tableInfo = tableInfo;
        }

        public Class<?> getMapperClass() {
            return mapperClass;
        }

        public void setMapperClass(Class<?> mapperClass) {
            this.mapperClass = mapperClass;
        }

        public Class<?> getModelClass() {
            return modelClass;
        }

        public void setModelClass(Class<?> modelClass) {
            this.modelClass = modelClass;
        }

        public TableInfo getTableInfo() {
            return tableInfo;
        }

        public void setTableInfo(TableInfo tableInfo) {
            this.tableInfo = tableInfo;
        }
    }


    /**
     * 当不能直接通过mappedStatement获取sql时,使用该mapper方法的@Select获取
     */
    protected final boolean getSqlWithAnnotation = true;

    /**
     * 是否只有在mapper类上有@QuerySupport时遍历方法列表获取support method
     */
    protected final boolean classShouldAnnotation = true;

    /**
     * 获取当前mapper所支持的查询方法并进行缓存
     *
     * @param mapperClass
     * @param modelClass
     * @param tableInfo
     * @return
     */
    protected String[] getSupportMethods(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        QuerySupport support = AnnotationUtils.findAnnotation(mapperClass, QuerySupport.class);
        Set<String> methodList = new HashSet<>(16);

        boolean loadClassMethod;
        if (support != null) {
            loadClassMethod = true;
            for (String value : support.value()) {
                methodList.add(value);
            }
        } else {
            loadClassMethod = !classShouldAnnotation;
        }

        if (loadClassMethod) {
            Method[] methods = ReflectionUtils.getAllDeclaredMethods(mapperClass);
            for (Method method : methods) {
                support = AnnotationUtils.findAnnotation(method, QuerySupport.class);
                String methodName = method.getName();
                if (support != null) {
                    methodList.add(methodName);
                }
                //存入support map中
                if (methodList.contains(methodName)) {
                    putSupportMap(mapperClass, method);
                }
            }
        }
        return methodList.toArray(new String[methodList.size()]);
    }

    /**
     * 缓存支持的方法
     *
     * @param mapperClass
     * @param method
     */
    public void putSupportMap(Class<?> mapperClass, Method method) {
        Map<String, Method> methodMap;
        if (supportMethodMap.containsKey(mapperClass)) {
            methodMap = supportMethodMap.get(mapperClass);
        } else {
            methodMap = new HashMap<>(16);
            supportMethodMap.put(mapperClass, methodMap);
        }
        methodMap.put(method.getName(), method);
    }

    public void removeSupportMap(Class<?> mapperClass) {
        supportMethodMap.remove(mapperClass);
    }

    public Method getSupportMethod(Class<?> mapperClass, String name) {
        Map<String, Method> methodMap = supportMethodMap.get(mapperClass);
        if (methodMap != null) {
            return methodMap.get(name);
        }
        return null;
    }

    public String getMethodSqlByAnnotation(Class<?> mapperClass, String name) {
        return getMethodSqlByAnnotation(mapperClass, name, getSupportMethod(mapperClass, name));
    }

    public String getMethodSqlByAnnotation(Class<?> mapperClass, String name, Method selectMethod) {
        String result = null;
        if (getSqlWithAnnotation) {
            String text = "Query Support find " + mapperClass.getName() + "." + name + " sql ";
            logger.debug(text + "by @Select");
            String error = text + "by @Select has error, cause by : ";
            Assert.notNull(selectMethod, error + "can't find method");
            Select select = AnnotationUtils.findAnnotation(selectMethod, Select.class);
            Assert.notNull(select, error + "not use @Select");
            try {
                StringBuilder sb = new StringBuilder();
                for (String value : select.value()) {
                    sb.append(value + "\t");
                }
                result = sb.toString();
            } catch (Exception e) {
                logger.warn(error + " {}", e.getMessage());
            }
        }

        return result;
    }

    public <T> T getField(Class<T> fieldClass, String fieldName, Object target) {
        Field field = ReflectionUtils.findField(target.getClass(), fieldName);
        field.setAccessible(true);
        return (T) ReflectionUtils.getField(field, target);

    }

    public String getSqlText(SqlNode sqlNode) {
        if (sqlNode != null) {
            if (sqlNode instanceof TextSqlNode) {
                return getField(String.class, "text", sqlNode);
            } else if (sqlNode instanceof MixedSqlNode) {
                List<SqlNode> sqlNodeList = getField(List.class, "contents", sqlNode);
                Assert.isTrue(sqlNodeList.size() == 1, "MixedSqlNode SqlNode contents should only one");
                return getSqlText(sqlNodeList.get(0));
            }
        }
        return null;
    }

    public String getSqlText(SqlSource sqlSource) {
        if (sqlSource != null) {
            if (sqlSource instanceof RawSqlSource) {
                return getSqlText(getField(RawSqlSource.class, "sqlSource", sqlSource));
            } else if (sqlSource instanceof StaticSqlSource) {
                return getField(String.class, "sql", sqlSource);
            } else if (sqlSource instanceof DynamicSqlSource) {
                SqlNode sqlNode = getField(SqlNode.class, "rootSqlNode", sqlSource);
                return getSqlText(sqlNode);
            }
        }
        return null;
    }

    /**
     * 重新注入@QuerySupport相关的方法
     *
     * @param mapperClass
     * @param modelClass
     * @param tableInfo
     */
    private void injectQuerySupportMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        String[] methodList = getSupportMethods(mapperClass, modelClass, tableInfo);
        String mapperClassName = mapperClass.getName();
        for (String method : methodList) {
            String statementName = mapperClassName + StringPool.DOT + method;
            Method selectMethod = getSupportMethod(mapperClass, method);
            Assert.notNull(selectMethod, "Query Support can't find method about " + statementName);
            boolean hasWrapper = false;
            for (Class parameterType : selectMethod.getParameterTypes()) {
                if (Wrapper.class.isAssignableFrom(parameterType)) {
                    hasWrapper = true;
                    break;
                }
            }

            MappedStatement mappedStatement = null;
            SqlSource sqlSource = null;
            try {
                mappedStatement = configuration.getMappedStatement(statementName);
                sqlSource = mappedStatement.getSqlSource();
            } catch (Exception e) {
                logger.warn("Query Support get statement error, cause by : {}", e.getMessage());
            }

            boolean buildSqlSource = false;

            String currentSql = null;
            if (sqlSource == null) {
                buildSqlSource = true;
                currentSql = getMethodSqlByAnnotation(mapperClass, method, selectMethod);
            } else {
                //when wrapper exist, should find sql text
                if (hasWrapper) {
                    buildSqlSource = true;
                    currentSql = getSqlText(sqlSource);
                }
            }

            if (buildSqlSource) {
                Assert.isTrue(StringUtils.isNotEmpty(currentSql), "Query Support can't correct find sql text about " + statementName);
                //logger.debug("Query Support find {} sql text: \r\n\t {}", statementName, currentSql);
                if (hasWrapper) {
                    String scriptSql;
                    if (currentSql.contains("<script>")) {
                        int index = currentSql.indexOf("</script>");
                        scriptSql = currentSql.substring(0, index) + " %s " + currentSql.substring(index);
                    } else {
                        scriptSql = "<script>\n" + currentSql + "\n %s </script>";
                    }
                    //生成支持查询条件的sql, mapper方法中需提供参数 @Param(Constants.WRAPPER) Wrapper<User> wrapper
                    currentSql = String.format(scriptSql, this.sqlWhereEntityWrapper(tableInfo));
                }
                sqlSource = languageDriver.createSqlSource(configuration, currentSql, modelClass);
            }

            try {
                //移除之前所应用的方法
                this.removeBeforeMappedStatement(statementName);
                this.addSelectMappedStatement(mapperClass, method, sqlSource, modelClass, tableInfo);
                logger.debug("Query Support afresh inject {} statement success", statementName);
            } catch (Exception e) {
                logger.warn("Query Support afresh inject statement error, cause by : {}", e.getMessage());
            }
        }
        removeSupportMap(mapperClass);
    }

    /**
     * 启动注入
     */
    public void injectQuerySupportMappedStatement() {
        for (QuerySupportProperty property : propertyList) {
            injectQuerySupportMappedStatement(property.getMapperClass(), property.getModelClass(), property.getTableInfo());
        }
    }


    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        /**
         * 仅用于记录mapper相关数据
         */
        QuerySupportProperty property = new QuerySupportProperty(mapperClass, modelClass, tableInfo);
        propertyList.add(property);
        return null;
    }


    /**
     * 修改所应用的resultMap/resultType  类型优先级 (若未存在注解,当类型与mapper泛型不一致时使用其本身) @ResultType > @ResultMap > @Table(在类型一致时应用存在的resultMap)
     *
     * @param mapperClass
     * @param id
     * @param sqlSource
     * @param resultType
     * @param table
     * @return
     */
    @Override
    protected MappedStatement addSelectMappedStatement(Class<?> mapperClass, String id, SqlSource sqlSource, Class<?> resultType, TableInfo table) {
        String resultMap = null;
        Method method = getSupportMethod(mapperClass, id);

        ResultType resultTypeAnnotation = AnnotationUtils.findAnnotation(method, ResultType.class);
        if (resultTypeAnnotation != null) {
            resultType = resultTypeAnnotation.value();
        } else {

            ResultMap resultMapAnnotation = AnnotationUtils.findAnnotation(method, ResultMap.class);
            if (resultMapAnnotation != null) {
                resultMap = resultMapAnnotation.value()[0];
            }

            /**
             * find method return result type
             */
            Class currentResultType = null;
            Type type = method.getGenericReturnType();
            if (type != null) {
                if (type instanceof ParameterizedType) {
                    currentResultType = (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
                } else if (type instanceof Class) {
                    currentResultType = (Class) type;
                }
            }

            if (resultType.equals(currentResultType)) {
                if (StringUtils.isEmpty(resultMap) && table != null) {
                    /**
                     * method return result type eq resultType apply resultMap
                     */
                    resultMap = table.getResultMap();
                }
            } else {
                resultType = currentResultType == null ? resultType : currentResultType;
            }
        }

        if (StringUtils.isNotEmpty(resultMap)) {
            return this.addMappedStatement(mapperClass, id, sqlSource, SqlCommandType.SELECT, (Class) null, resultMap, (Class) null, new NoKeyGenerator(), (String) null, (String) null);
        }
        return this.addMappedStatement(mapperClass, id, sqlSource, SqlCommandType.SELECT, (Class) null, (String) null, resultType, new NoKeyGenerator(), (String) null, (String) null);
    }

    /**
     * 移除之前所注册的mappedStatement
     *
     * @param statementName mapper方法所对应的id
     */
    protected void removeBeforeMappedStatement(String statementName) {
        try {
            if (configuration.hasStatement(statementName)) {
                configuration.getMappedStatementNames().remove(statementName);
            }
        } catch (Exception e) {
            logger.warn("Query Support remove before statement error, cause by : {}", e.getMessage());
        }
    }

}