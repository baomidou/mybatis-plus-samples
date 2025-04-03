package com.baomidou.mybatisplus.samples.mysql;

import com.baomidou.mybatisplus.extension.ddl.DdlScript;
import com.baomidou.mybatisplus.samples.mysql.config.DdlConfig;
import com.baomidou.mybatisplus.samples.mysql.config.MysqlDdl;
import com.baomidou.mybatisplus.samples.mysql.entity.TestData;
import com.baomidou.mybatisplus.samples.mysql.enums.TestEnum;
import com.baomidou.mybatisplus.samples.mysql.mapper.TestDataMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled
@SpringBootTest
class MysqlTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MysqlTest.class);

    @Autowired
    private TestDataMapper testDataMapper;

    @Autowired
    private DdlScript ddlScript;
    @Autowired
    private MysqlDdl mysqlDdl;

    @BeforeEach
    void truncateTable() throws Exception {
        // ddlScript.run(mysqlDdl.getSqlFiles());
        ddlScript.run(new StringReader("TRUNCATE TABLE test_data;"));
    }

    @Test
    void initTable() {
        ddlScript.run(mysqlDdl.getSqlFiles());
    }
    @Test
    @Order(1)
    void insertBatch() {
        int size = 9;
        List<TestData> testDataList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            String str = i + "条";
            testDataList.add(new TestData().setTestInt(i).setTestEnum(TestEnum.TWO).setTestStr(str));
        }
        assertEquals(size, testDataMapper.insertBatchSomeColumn(testDataList));
        testDataList.forEach(data-> LOGGER.info("testData:{}", data));
    }

    @Test
    @Order(3)
    void selectAll() {

        int size = 9;
        List<TestData> testDataList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            String str = i + "条";
            testDataList.add(new TestData().setTestInt(i).setTestEnum(TestEnum.TWO).setTestStr(str));
        }
        assertEquals(size, testDataMapper.insertBatchSomeColumn(testDataList));
        testDataList.forEach(System.err::println);
    }

    @Test
    @Order(2)
    void testInsertAutoId() {
        // 自增 id 增长为 1
        TestData testData = new TestData().setTestInt(1);
        testData.setTestEnum(TestEnum.ONE).setTestStr("abc");
        Assertions.assertEquals(testDataMapper.insert(testData), 1);
        testData = testDataMapper.selectById(testData.getId());
        Assertions.assertNotNull(testData);
        Assertions.assertEquals(1, testData.getId());
        LOGGER.info("testData:{}", testData);
        // 自增 id 增长为 2
        TestData testData2 = new TestData().setTestInt(1);
        testData2.setTestEnum(TestEnum.TWO).setTestStr("def");
        Assertions.assertEquals(testDataMapper.insert(testData2), 1);
        testData2 = testDataMapper.selectById(testData2.getId());
        Assertions.assertNotNull(testData2);
        Assertions.assertEquals(2, testData2.getId());
        LOGGER.info("testData:{}", testData2);
    }

    @Test
    @Order(3)
    void testMysqlInsertAllBatch() {
        List<TestData> testDataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String str = i + "条";
            testDataList.add(new TestData().setTestInt(i).setTestEnum(TestEnum.TWO).setTestStr(str));
        }
        testDataMapper.mysqlInsertAllBatch(testDataList);
        testDataList.forEach(data-> LOGGER.info("testData:{}", data));
    }

}
