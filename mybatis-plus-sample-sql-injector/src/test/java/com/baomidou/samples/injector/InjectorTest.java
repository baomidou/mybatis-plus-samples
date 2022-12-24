package com.baomidou.samples.injector;

import com.baomidou.samples.injector.entity.Student;
import com.baomidou.samples.injector.mapper.StudentMapper;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义注入测试
 *
 * @author nieqiurong 2018/8/11 20:34.
 */
@Slf4j
@SpringBootTest
public class InjectorTest {

    @Autowired
    private StudentMapper studentMapper;

    @Test
    public void test() {
        log.error("--------------------------------------insert-------------------------------------------------------");
        List<Long> ids = Lists.newArrayList();
        for (int i = 0; i < 2; i++) {
            Student student = new Student("小明" + i + "号", i);
            studentMapper.insert(student);
            ids.add(student.getId());
        }
        log.error("--------------------------------------insertBatchSomeColumn-------------------------------------------------------");
        List<Student> ss = Lists.newArrayList();
        for (int i = 2; i < 20; i++) {
            Student student = new Student("小明" + i + "号", i);
            ss.add(student);
        }
        studentMapper.insertBatchSomeColumn(ss);
        ids.addAll(ss.stream().map(Student::getId).collect(Collectors.toList()));

        Student select = studentMapper.select("select * from student where id = " + ids.get(0));
        System.out.println(select);

        log.error("--------------------------------------deleteAll-------------------------------------------------------");
        studentMapper.deleteAll();
    }
}
