package com.baomidou.mybatisplus.samples.jsonb;

import com.baomidou.mybatisplus.samples.jsonb.entity.TestContent;
import com.baomidou.mybatisplus.samples.jsonb.entity.TestData;
import com.baomidou.mybatisplus.samples.jsonb.mapper.TestDataMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@Disabled
@SpringBootTest
public class JsonbTest {
    @Autowired
    private TestDataMapper testDataMapper;

    @Test
    public void test() {
        TestData testData = new TestData();
        testData.setContent(TestContent.of("hi", "flowlong"));
        testData.setContentList(Arrays.asList(TestContent.of("name", "秋秋"), TestContent.of("name", "哈哈")));
        testDataMapper.insert(testData);
        TestData dbTestData = testDataMapper.selectById(testData.getId());
        System.out.println(dbTestData.getContent());
        Assertions.assertEquals(testData.getContent().getTitle(), dbTestData.getContent().getTitle());
        Assertions.assertEquals(testData.getContentList().size(), 2);
        for (TestContent testContent : testData.getContentList()) {
            Assertions.assertEquals("name", testContent.getTitle());
            Assertions.assertTrue(testContent.getContent().equals("秋秋") || testContent.getContent().equals("哈哈"));
        }
    }

}
