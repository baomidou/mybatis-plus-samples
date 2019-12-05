package com.baomidou.samples.injector.mapper;

import com.baomidou.samples.injector.base.MyBaseMapper;
import com.baomidou.samples.injector.entity.Student;
import org.apache.ibatis.annotations.Mapper;

/**
 * 学生Mapper层
 *
 * @author nieqiurong 2018/8/11 20:21.
 */
@Mapper
public interface StudentMapper extends MyBaseMapper<Student> {
}
