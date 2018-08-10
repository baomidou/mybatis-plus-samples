package com.baomidou.mybatisplus.samples.pagination.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.samples.pagination.entity.User;
import com.baomidou.mybatisplus.samples.pagination.model.MyPage;
import org.apache.ibatis.annotations.Param;

/**
 * @author miemie
 * @since 2018-08-10
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 3.x 的 page 可以进行取值,多入参记得加上注解
     *
     * @param myPage 自定义 page
     * @return 分页数据
     */
    MyPage<User> mySelectPage(@Param("pg") MyPage<User> myPage);
}
