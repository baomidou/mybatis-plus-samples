package com.baomidou.samples.metainfo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.samples.metainfo.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Mapper层
 * @author nieqiurong 2018-08-10 22:54:51.
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
