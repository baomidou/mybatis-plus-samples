package com.baomidou.mybatisplus.samples.reduce.springmvc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.samples.reduce.springmvc.entity.User;

public interface UserMapper extends BaseMapper<User> {

    Integer selectMaxAge();

}
