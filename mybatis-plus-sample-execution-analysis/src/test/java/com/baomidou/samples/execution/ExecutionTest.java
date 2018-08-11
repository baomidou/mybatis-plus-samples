package com.baomidou.samples.execution;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.samples.execution.mapper.StudentMapper;

/**
 * 执行分析测试
 * @author nieqiurong 2018/8/11 21:21.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ExecutionTest {

    @Autowired
    private StudentMapper studentMapper;

    @Test
    public void test(){
        studentMapper.selectList(new QueryWrapper<>());
        studentMapper.delete(new QueryWrapper<>());
    }

}
