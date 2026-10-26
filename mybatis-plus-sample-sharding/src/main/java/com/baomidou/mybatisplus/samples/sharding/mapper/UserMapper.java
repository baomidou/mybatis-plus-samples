package com.baomidou.mybatisplus.samples.sharding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.datasource.Sharding;
import com.baomidou.mybatisplus.samples.sharding.entity.User;
import com.baomidou.mybatisplus.samples.sharding.config.IdShardingStrategy;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
@Sharding("orders")
public interface UserMapper extends BaseMapper<User> {

    @Sharding(value = "orders", strategy = IdShardingStrategy.class)
    User selectById(Long id);

    @Sharding("inventory")
    @Select("select id, name from t_user where id = 1")
    User selectFromInventory();
}
