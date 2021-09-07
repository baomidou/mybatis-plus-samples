package com.baomidou.samples.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.samples.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
