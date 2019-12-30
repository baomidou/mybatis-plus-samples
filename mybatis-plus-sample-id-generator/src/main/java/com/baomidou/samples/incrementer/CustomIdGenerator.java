package com.baomidou.samples.incrementer;

import java.util.concurrent.atomic.AtomicLong;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义ID生成器
 * 仅作为示范
 *
 * @author nieqiuqiu 2019/11/30
 */
@Slf4j
@Component
public class CustomIdGenerator implements IdentifierGenerator {

    private final AtomicLong al = new AtomicLong(1);

    @Override
    public Long nextId(Object entity) {
        //可以将当前传入的class全类名来作为bizKey,或者提取参数来生成bizKey进行分布式Id调用生成.
        String bizKey = entity.getClass().getName();
        log.info("bizKey:{}", bizKey);
        MetaObject metaObject = SystemMetaObject.forObject(entity);
        String name = (String) metaObject.getValue("name");
        final long id = al.getAndAdd(1);
        log.info("为{}生成主键值->:{}", name, id);
        return id;
    }
}
