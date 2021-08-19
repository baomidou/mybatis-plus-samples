package com.baomidou.mybatisplus.samples.mysql.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.samples.mysql.config.MyBaseMapper;
import com.baomidou.mybatisplus.samples.mysql.entity.TestData;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

public interface TestDataMapper extends MyBaseMapper<TestData> {

    @Select("select * from test_data")
    List<TestData> getAllNoTenant();

    @Select("select * from test_data ${ew.customSqlSegment}")
    List<TestData> getByWrapper(@Param(Constants.WRAPPER) Wrapper wrapper);

    @Select("select * from test_data where id = #{ooxx}")
    Optional<TestData> getById(Long id);

    @Select("select * from test_data")
    Page<TestData> myPage(Page<TestData> page);
}
