package com.baomidou.samples.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.samples.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author nieqiuqiu 2019/11/30
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
