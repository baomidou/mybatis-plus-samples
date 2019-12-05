package com.baomidou.samples.injector;

import com.baomidou.samples.injector.mapper.StudentMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * 自定义注入测试
 * @author nieqiurong 2018/8/11 20:34.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class InjectorTest {

    @Resource
    private StudentMapper studentMapper;

    @Test
    public void test(){
        studentMapper.deleteAll();
    }
}
