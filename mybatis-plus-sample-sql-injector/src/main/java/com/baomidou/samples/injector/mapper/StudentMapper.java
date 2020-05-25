package com.baomidou.samples.injector.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import com.baomidou.samples.injector.base.MyBaseMapper;
import com.baomidou.samples.injector.config.MySelectProvider;
import com.baomidou.samples.injector.entity.Student;

/**
 * 学生Mapper层
 *
 * @author nieqiurong 2018/8/11 20:21.
 */
@Mapper
public interface StudentMapper extends MyBaseMapper<Student> {

    @SelectProvider(value = MySelectProvider.class, method = "getSql")
    Student select(String sql);
}
