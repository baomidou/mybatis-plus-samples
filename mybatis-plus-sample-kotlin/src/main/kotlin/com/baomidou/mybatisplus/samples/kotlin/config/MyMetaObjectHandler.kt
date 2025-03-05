package com.baomidou.mybatisplus.samples.kotlin.config

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler
import org.apache.ibatis.reflection.MetaObject
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.LocalDateTime

/**
 * @author nieqiurong
 */
@Component
class MyMetaObjectHandler : MetaObjectHandler {

    private val log = LoggerFactory.getLogger(MyMetaObjectHandler::class.java)

    // 注意将kotlin类型转为java类型请使用 xxx::class.javaObjectType，防止部分类型使用xxx::class.java转换为基本类型导致类型不一致无法填充
    override fun insertFill(metaObject: MetaObject) {
        log.info("开始插入填充...");
        this.strictInsertFill(metaObject, "createUserId", Long::class.javaObjectType, 1111L)
        this.strictInsertFill(metaObject, "createTime", LocalDateTime::class.javaObjectType, LocalDateTime.now())
    }

    override fun updateFill(metaObject: MetaObject) {
        log.info("开始更新填充...");
        this.strictUpdateFill(metaObject, "updateUserId", Long::class.javaObjectType, 6666L)
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime::class.javaObjectType, LocalDateTime.now())
    }

}