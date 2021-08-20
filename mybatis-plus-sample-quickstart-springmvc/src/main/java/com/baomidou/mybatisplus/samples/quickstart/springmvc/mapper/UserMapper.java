package com.baomidou.mybatisplus.samples.quickstart.springmvc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.samples.quickstart.springmvc.entity.User;

public interface UserMapper extends BaseMapper<User> {

    Integer selectMaxAge();

}
