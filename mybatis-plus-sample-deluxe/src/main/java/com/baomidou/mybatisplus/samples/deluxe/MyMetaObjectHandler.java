package com.baomidou.mybatisplus.samples.deluxe;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * 填充器
 *
 * @author nieqiurong 2018-08-10 22:59:23.
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyMetaObjectHandler.class);

    @Override
    public void insertFill(MetaObject metaObject) {
        LOGGER.info("start insert fill ....");
        //避免使用metaObject.setValue()
        this.strictInsertFill(metaObject, "createTime", Timestamp.class, new Timestamp(System.currentTimeMillis()));
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        LOGGER.info("nothing to fill ....");
    }
}
