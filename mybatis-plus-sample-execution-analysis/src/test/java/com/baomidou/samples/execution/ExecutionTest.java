package com.baomidou.samples.execution;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.MyBatisSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.samples.execution.entity.Student;
import com.baomidou.samples.execution.mapper.StudentMapper;

/**
 * 执行分析测试
 * @author nieqiurong 2018/8/11 21:21.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ExecutionTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExecutionTest.class);

    @Autowired
    private StudentMapper studentMapper;

    @Test
    public void test(){
        studentMapper.selectList(new QueryWrapper<>());
        studentMapper.deleteById(1L);
        Student student = new Student();
        student.setName("test_update");
        studentMapper.insert(new Student(1L,"test",12));
        studentMapper.update(student,new QueryWrapper<Student>().eq("id",1L));
        try {
            studentMapper.update(new Student(),new QueryWrapper<>());
        }catch (MyBatisSystemException e){
        }
        try {
            studentMapper.delete(new QueryWrapper<>());
        }catch (MyBatisSystemException e){

        }
        Assert.notEmpty(studentMapper.selectList(new QueryWrapper<>()),"数据都被删掉了.(┬＿┬)");
    }

}
