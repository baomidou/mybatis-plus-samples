package com.baomidou.samples.performance;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.samples.performance.mapper.StudentMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 性能分析测试
 * @author nieqiurong 2018/8/11 21:14.
 */
@SpringBootTest
public class PerformanceTest {

    @Autowired
    private StudentMapper studentMapper;

    @Test
    public void test(){
        studentMapper.selectList(new QueryWrapper<>());
    }

}
