package com.baomidou;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.core.mapper.Mapper;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Set;

@Configuration
public class MybatisPlusConfig {

    // 模拟并行注入性能测试(请注意,低版本的mybatis缓存的为hashmap结构,并不能并行注入,mp3.5.6开始通过mybatis源码修改为了ConcurrentHashMap)
//    @Bean
    public ISqlInjector sqlInjector() {
        return new DefaultSqlInjector() {
            @Override
            public void inspectInject(MapperBuilderAssistant builderAssistant, Class<?> mapperClass) {
                Class<?> modelClass = ReflectionKit.getSuperClassGenericType(mapperClass, Mapper.class, 0);
                if (modelClass != null) {
                    String className = mapperClass.toString();
                    Set<String> mapperRegistryCache = GlobalConfigUtils.getMapperRegistryCache(builderAssistant.getConfiguration());
                    if (!mapperRegistryCache.contains(className)) {
                        TableInfo tableInfo = TableInfoHelper.initTableInfo(builderAssistant, modelClass);
                        List<AbstractMethod> methodList = this.getMethodList(builderAssistant.getConfiguration(), mapperClass, tableInfo);
                        if (CollectionUtils.isNotEmpty(methodList)) {
                            //TODO 修改为并行注入看看
                            methodList.stream().parallel().forEach(m -> m.inject(builderAssistant, mapperClass, modelClass, tableInfo));
                        } else {
                            logger.debug(className + ", No effective injection method was found.");
                        }
                        mapperRegistryCache.add(className);
                    }
                }
            }
        };
    }

}
