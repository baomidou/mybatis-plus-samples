package com.baomidou.mybatisplus.samples.tenant.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.samples.tenant.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * MP 支持不需要 UserMapper.xml 这个模块演示内置 CRUD 咱们就不要 XML 部分了
 * </p>
 *
 * @author hubin
 * @since 2018-08-11
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 自定义SQL：默认也会增加多租户条件
     * 参考打印的SQL
     * @return
     */
    Integer myCount();

    List<User> getUserAndAddr(@Param("username") String username);

    List<User> getAddrAndUser(@Param("name") String name);
}
