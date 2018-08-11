package com.baomidou.mybatisplus.samples.pagination.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.samples.pagination.entity.User;
import com.baomidou.mybatisplus.samples.pagination.model.MyPage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

/**
 * @author miemie
 * @since 2018-08-10
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 3.x 的 page 可以进行取值,多入参记得加上注解
     * todo 目前使用注解会报错,写在 xml 里就没事
     *
     * @param myPage 自定义 page
     * @return 分页数据
     */
    @Select("select * from user where age = #{pg.selectInt} and name = #{pg.selectStr}")
    @ResultType(User.class)
    IPage<User> mySelectPage(@Param("pg") MyPage<User> myPage);
}
