package com.baomidou.samples.incrementer;

import com.baomidou.mybatisplus.core.incrementer.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

/**
 * 自定义ID生成器
 *
 * @author nieqiuqiu 2019/11/30
 */
@Slf4j
public class CustomIdGenerator implements IdGenerator {

    @Override
    public Long generate(Object entity) {
        MetaObject metaObject = SystemMetaObject.forObject(entity);
        String name = (String) metaObject.getValue("name");
        log.info("开始为:{},生成主键值!", name);
        return 66666L;
    }

}
