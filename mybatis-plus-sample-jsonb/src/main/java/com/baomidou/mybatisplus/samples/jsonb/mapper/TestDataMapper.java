package com.baomidou.mybatisplus.samples.jsonb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.samples.jsonb.entity.TestData;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestDataMapper extends BaseMapper<TestData> {

}
