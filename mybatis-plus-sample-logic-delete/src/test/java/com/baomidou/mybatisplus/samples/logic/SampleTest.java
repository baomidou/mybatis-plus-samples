package com.baomidou.mybatisplus.samples.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.assertj.core.util.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.samples.logic.entity.Common;
import com.baomidou.mybatisplus.samples.logic.mapper.CommonMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleTest {

    @Resource
    private CommonMapper commonMapper;

    @Test
    public void test() {
        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Common common = new Common().setName("" + i);
            commonMapper.insert(common);
            ids.add(common.getId());
        }
        log.error("--------------------------------------------------------------------------------------------------------");
        commonMapper.deleteById(ids.remove(0));
        log.error("--------------------------------------------------------------------------------------------------------");
        commonMapper.deleteBatchIds(Arrays.asList(ids.remove(0), ids.remove(0), ids.remove(0)));
        log.error("--------------------------------------------------------------------------------------------------------");
        commonMapper.delete(Wrappers.<Common>query().eq("id", ids.remove(0)));
        log.error("--------------------------------------------------------------------------------------------------------");
        commonMapper.deleteByMap(Maps.newHashMap("id", ids.remove(0)));
    }
}
