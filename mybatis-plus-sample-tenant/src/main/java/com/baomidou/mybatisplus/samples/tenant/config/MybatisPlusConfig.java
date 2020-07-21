package com.baomidou.mybatisplus.samples.tenant.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;

/**
 * @author miemie
 * @since 2018-08-10
 */
@Configuration
@MapperScan("com.baomidou.mybatisplus.samples.tenant.mapper")
public class MybatisPlusConfig {

    /**
     * 老多租户属于 SQL 解析部分，依赖 MP 分页插件
     */
//    @Bean
//    public PaginationInterceptor paginationInterceptor() {
//        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
//        /*
//         * 【测试多租户】 SQL 解析处理拦截器<br>
//         * 这里固定写成住户 1 实际情况你可以从cookie读取，因此数据看不到 【 麻花藤 】 这条记录（ 注意观察 SQL ）<br>
//         */
//        List<ISqlParser> sqlParserList = new ArrayList<>();
//        TenantSqlParser tenantSqlParser = new MyTenantParser();
//        tenantSqlParser.setTenantHandler(new TenantHandler() {
//
//            /**
//             * 2019-8-1
//             *
//             * https://gitee.com/baomidou/mybatis-plus/issues/IZZ3M
//             *
//             * tenant_id in (1,2)
//             * @param  where 如果是where，可以追加，不是where的情况：比如当insert时，不能insert into user(name, tenant_id) values('test', tenant_id IN (1, 2));
//             * @return
//             */
//            @Override
//            public Expression getTenantId(boolean where) {
//                final boolean multipleTenantIds = true;//这里只是演示切换单个tenantId和多个tenantId
//                //具体场景，可以根据情况来拼接
//                if (where && multipleTenantIds) {
//                    //演示如何实现tenant_id in (1,2)
//                    return multipleTenantIdCondition();
//                } else {
//                    //演示：tenant_id=1
//                    return singleTenantIdCondition();
//                }
//            }
//
//            private Expression singleTenantIdCondition() {
//                return new LongValue(1);//ID自己想办法获取到
//            }
//
//            private Expression multipleTenantIdCondition() {
//                final InExpression inExpression = new InExpression();
//                inExpression.setLeftExpression(new Column(getTenantIdColumn()));
//                final ExpressionList itemsList = new ExpressionList();
//                final List<Expression> inValues = new ArrayList<>(2);
//                inValues.add(new LongValue(1));//ID自己想办法获取到
//                inValues.add(new LongValue(2));
//                itemsList.setExpressions(inValues);
//                inExpression.setRightItemsList(itemsList);
//                return inExpression;
//            }
//
//            @Override
//            public String getTenantIdColumn() {
//                return "tenant_id";
//            }
//
//            @Override
//            public boolean doTableFilter(String tableName) {
//                // 这里可以判断是否过滤表
//                /*if ("user".equals(tableName)) {
//                    return true;
//                }*/
////                return false;
//                return !"user".equalsIgnoreCase(tableName);
//            }
//
//        });
//
//        sqlParserList.add(tenantSqlParser);
//        paginationInterceptor.setSqlParserList(sqlParserList);
////        paginationInterceptor.setSqlParserFilter(new ISqlParserFilter() {
////            @Override
////            public boolean doFilter(MetaObject metaObject) {
////                MappedStatement ms = SqlParserHelper.getMappedStatement(metaObject);
////                // 过滤自定义查询此时无租户信息约束【 麻花藤 】出现
////                if ("com.baomidou.springboot.mapper.UserMapper.selectListBySQL".equals(ms.getId())) {
////                    return true;
////                }
////                return false;
////            }
////        });
//        return paginationInterceptor;
//    }

    /**
     * 新多租户插件配置,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存万一出现问题
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new TenantLineHandler() {
            @Override
            public Expression getTenantId() {
                return new LongValue(1);
            }

            // 这是 default 方法,默认返回 false 表示所有表都需要拼多租户条件
            @Override
            public boolean ignoreTable(String tableName) {
                return !"user".equalsIgnoreCase(tableName);
            }
        }));
        // 如果用了分页插件注意先 add TenantLineInnerInterceptor 再 add PaginationInnerInterceptor
        // 用了分页插件必须设置 MybatisConfiguration#useDeprecatedExecutor = false
//        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> configuration.setUseDeprecatedExecutor(false);
    }
}
