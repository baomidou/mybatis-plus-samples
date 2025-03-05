package com.baomidou.mybatisplus.samples.kotlin.entity

import com.baomidou.mybatisplus.annotation.FieldFill
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableName
import java.time.LocalDateTime

/**
 * @author nieqiurong
 */
@TableName("sys_user")
class User {

    var id: Long? = null

    var name: String? = null

    var age: Int? = null

    var email: String? = null

    @TableField(exist = false)
    var ignoreColumn = "ignoreColumn"

    @TableField(fill = FieldFill.INSERT)
    var createUserId: Long? = null

    @TableField(fill = FieldFill.INSERT)
    var createTime: LocalDateTime? = null

    @TableField(fill = FieldFill.UPDATE)
    var updateUserId: Long? = null

    @TableField(fill = FieldFill.UPDATE)
    var updateTime: LocalDateTime? = null

}