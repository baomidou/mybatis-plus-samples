package com.baomidou.samples.injector.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.samples.injector.entity.Student;

/**
 * 学生Mapper层
 * @author nieqiurong 2018/8/11 20:21.
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {

    void deleteAll();

}
