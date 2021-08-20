package com.baomidou.samples.execution;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.samples.execution.entity.Student;
import com.baomidou.samples.execution.mapper.StudentMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 执行分析测试
 *
 * @author nieqiurong 2018/8/11 21:21.
 */
@Slf4j
@SpringBootTest
class ExecutionTest {

    @Autowired
    private StudentMapper studentMapper;

    @Test
    void test() {
        studentMapper.selectList(new QueryWrapper<>());
        studentMapper.deleteById(1L);
        Student student = new Student();
        student.setName("test_update");
        studentMapper.insert(new Student(1L, "test", 12));
        studentMapper.update(student, new QueryWrapper<Student>().eq("id", 1L));
        try {
            studentMapper.update(new Student(), new QueryWrapper<>());
        } catch (MyBatisSystemException e) {
        }
        try {
            studentMapper.delete(new QueryWrapper<>());
        } catch (MyBatisSystemException e) {
            System.err.println("执行了全表删除拦截，删除无效！异常：" + e.getMessage());
        }
        Assertions.assertTrue(CollectionUtils.isNotEmpty(studentMapper.selectList(new QueryWrapper<>())), "数据都被删掉了.(┬＿┬)");
    }
}
