package com.baomidou.samples.incrementer;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.stereotype.Component;

/**
 * 自定义ID生成器
 *
 * @author nieqiuqiu 2019/11/30
 */
@Slf4j
@Component
public class CustomIdGenerator implements IdentifierGenerator {

    @Override
    public Long nextId(Object entity) {
        MetaObject metaObject = SystemMetaObject.forObject(entity);
        String name = (String) metaObject.getValue("name");
        log.info("开始为:{},生成主键值!", name);
        return 66666L;
    }

}
