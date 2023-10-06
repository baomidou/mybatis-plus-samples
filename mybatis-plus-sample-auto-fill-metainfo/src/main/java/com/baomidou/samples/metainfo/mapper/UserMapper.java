package com.baomidou.samples.metainfo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.samples.metainfo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户Mapper层
 * @author nieqiurong 2018-08-10 22:54:51.
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    void testMyMethod1(@Param("coll") List<User> userList);

    void testMyMethod2(@Param("list") List<User> userList);

    void testMyMethod3(@Param("collection") List<User> userList);

    void testMyMethod4(List<User> userList);

    void testMyMethod5(@Param("userList") List<User> userList);
}
