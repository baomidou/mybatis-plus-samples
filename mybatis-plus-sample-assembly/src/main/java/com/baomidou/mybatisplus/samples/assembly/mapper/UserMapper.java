package com.baomidou.mybatisplus.samples.assembly.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.samples.assembly.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author nieqiuqiu
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
