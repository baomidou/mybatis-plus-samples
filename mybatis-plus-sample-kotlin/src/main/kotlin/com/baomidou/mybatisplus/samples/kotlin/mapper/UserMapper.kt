package com.baomidou.mybatisplus.samples.kotlin.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.samples.kotlin.entity.User
import org.apache.ibatis.annotations.Mapper

/**
 * @author nieqiurong
 */
@Mapper
interface UserMapper : BaseMapper<User> {

    fun user(id: Int): User {
        return selectById(id)
    }

}