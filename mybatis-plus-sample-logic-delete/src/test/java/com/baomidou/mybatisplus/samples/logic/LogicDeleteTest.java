package com.baomidou.mybatisplus.samples.logic;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.samples.logic.entity.Common;
import com.baomidou.mybatisplus.samples.logic.entity.Null1;
import com.baomidou.mybatisplus.samples.logic.entity.Null2;
import com.baomidou.mybatisplus.samples.logic.mapper.CommonMapper;
import com.baomidou.mybatisplus.samples.logic.mapper.Null1Mapper;
import com.baomidou.mybatisplus.samples.logic.mapper.Null2Mapper;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Maps;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
@SpringBootTest
public class LogicDeleteTest {
    @Autowired
    private CommonMapper commonMapper;
    @Autowired
    private Null1Mapper null1Mapper;
    @Autowired
    private Null2Mapper null2Mapper;

    @Test
    public void tCommon() {
        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Common common = new Common().setName("" + i);
            commonMapper.insert(common);
            ids.add(common.getId());
        }
        log.info("------------------------------------------------deleteById--------------------------------------------------------");
        commonMapper.deleteById(ids.remove(0));
        log.info("------------------------------------------------deleteByMap--------------------------------------------------------");
        commonMapper.deleteByMap(Maps.newHashMap("id", ids.remove(0)));
        log.info("------------------------------------------------delete--------------------------------------------------------");
        commonMapper.delete(Wrappers.<Common>query().eq("id", ids.remove(0)));
        log.info("------------------------------------------------deleteBatchIds--------------------------------------------------------");
        commonMapper.deleteBatchIds(Arrays.asList(ids.remove(0), ids.remove(0)));
        log.info("------------------------------------------------updateById--------------------------------------------------------");
        commonMapper.updateById(new Common().setId(ids.remove(0)).setName("老王"));
        log.info("------------------------------------------------update--------------------------------------------------------");
        commonMapper.update(new Common().setName("老王"), Wrappers.<Common>update().eq("id", ids.remove(0)));
        log.info("------------------------------------------------selectById--------------------------------------------------------");
        commonMapper.selectById(ids.remove(0));
        log.info("------------------------------------------------selectBatchIds--------------------------------------------------------");
        commonMapper.selectBatchIds(Arrays.asList(ids.remove(0), ids.remove(0)));
        log.info("------------------------------------------------selectByMap--------------------------------------------------------");
        commonMapper.selectByMap(Maps.newHashMap("id", ids.remove(0)));
        log.info("------------------------------------------------selectOne--------------------------------------------------------");
        commonMapper.selectOne(Wrappers.<Common>query().eq("id", ids.remove(0)));
        log.info("------------------------------------------------selectCount--------------------------------------------------------");
        commonMapper.selectCount(Wrappers.<Common>query().eq("id", ids.remove(0)));
        log.info("------------------------------------------------selectList--------------------------------------------------------");
        commonMapper.selectList(Wrappers.<Common>query().eq("id", ids.remove(0)));
        log.info("------------------------------------------------selectMaps--------------------------------------------------------");
        commonMapper.selectMaps(Wrappers.<Common>query().eq("id", ids.remove(0)));
        log.info("------------------------------------------------selectObjs--------------------------------------------------------");
        commonMapper.selectObjs(Wrappers.<Common>query().select("id").eq("id", ids.remove(0)));
        log.info("------------------------------------------------selectPage--------------------------------------------------------");
        commonMapper.selectPage(new Page<>(), Wrappers.<Common>query().eq("id", ids.remove(0)));
        log.info("------------------------------------------------selectMapsPage--------------------------------------------------------");
        commonMapper.selectMapsPage(new Page<>(), Wrappers.<Common>query().eq("id", ids.remove(0)));
    }

    @Test
    public void tNull1() {
        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Null1 null1 = new Null1().setName("" + i).setDeleted(1);
            null1Mapper.insert(null1);
            ids.add(null1.getId());
        }
        log.info("------------------------------------------------deleteById--------------------------------------------------------");
        null1Mapper.deleteById(ids.remove(0));
        log.info("------------------------------------------------deleteByMap--------------------------------------------------------");
        null1Mapper.deleteByMap(Maps.newHashMap("id", ids.remove(0)));
        log.info("------------------------------------------------delete--------------------------------------------------------");
        null1Mapper.delete(Wrappers.<Null1>query().eq("id", ids.remove(0)));
        log.info("------------------------------------------------deleteBatchIds--------------------------------------------------------");
        null1Mapper.deleteBatchIds(Arrays.asList(ids.remove(0), ids.remove(0)));
        log.info("------------------------------------------------updateById--------------------------------------------------------");
        null1Mapper.updateById(new Null1().setId(ids.remove(0)).setName("老王"));
        log.info("------------------------------------------------update--------------------------------------------------------");
        null1Mapper.update(new Null1().setName("老王"), Wrappers.<Null1>update().eq("id", ids.remove(0)));
        log.info("------------------------------------------------selectById--------------------------------------------------------");
        null1Mapper.selectById(ids.remove(0));
        log.info("------------------------------------------------selectBatchIds--------------------------------------------------------");
        null1Mapper.selectBatchIds(Arrays.asList(ids.remove(0), ids.remove(0)));
        log.info("------------------------------------------------selectByMap--------------------------------------------------------");
        null1Mapper.selectByMap(Maps.newHashMap("id", ids.remove(0)));
        log.info("------------------------------------------------selectOne--------------------------------------------------------");
        null1Mapper.selectOne(Wrappers.<Null1>query().eq("id", ids.remove(0)));
        log.info("------------------------------------------------selectCount--------------------------------------------------------");
        null1Mapper.selectCount(Wrappers.<Null1>query().eq("id", ids.remove(0)));
        log.info("------------------------------------------------selectList--------------------------------------------------------");
        null1Mapper.selectList(Wrappers.<Null1>query().eq("id", ids.remove(0)));
        log.info("------------------------------------------------selectMaps--------------------------------------------------------");
        null1Mapper.selectMaps(Wrappers.<Null1>query().eq("id", ids.remove(0)));
        log.info("------------------------------------------------selectObjs--------------------------------------------------------");
        null1Mapper.selectObjs(Wrappers.<Null1>query().select("id").eq("id", ids.remove(0)));
        log.info("------------------------------------------------selectPage--------------------------------------------------------");
        null1Mapper.selectPage(new Page<>(), Wrappers.<Null1>query().eq("id", ids.remove(0)));
        log.info("------------------------------------------------selectMapsPage--------------------------------------------------------");
        null1Mapper.selectMapsPage(new Page<>(), Wrappers.<Null1>query().eq("id", ids.remove(0)));
    }

    @Test
    public void tNull2() {
        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Null2 null2 = new Null2().setName("" + i);
            null2Mapper.insert(null2);
            ids.add(null2.getId());
        }
        log.info("------------------------------------------------deleteById--------------------------------------------------------");
        null2Mapper.deleteById(ids.remove(0));
        log.info("------------------------------------------------deleteByMap--------------------------------------------------------");
        null2Mapper.deleteByMap(Maps.newHashMap("id", ids.remove(0)));
        log.info("------------------------------------------------delete--------------------------------------------------------");
        null2Mapper.delete(Wrappers.<Null2>query().eq("id", ids.remove(0)));
        log.info("------------------------------------------------deleteBatchIds--------------------------------------------------------");
        null2Mapper.deleteBatchIds(Arrays.asList(ids.remove(0), ids.remove(0)));
        log.info("------------------------------------------------updateById--------------------------------------------------------");
        null2Mapper.updateById(new Null2().setId(ids.remove(0)).setName("老王"));
        log.info("------------------------------------------------update--------------------------------------------------------");
        null2Mapper.update(new Null2().setName("老王"), Wrappers.<Null2>update().eq("id", ids.remove(0)));
        log.info("------------------------------------------------selectById--------------------------------------------------------");
        null2Mapper.selectById(ids.remove(0));
        log.info("------------------------------------------------selectBatchIds--------------------------------------------------------");
        null2Mapper.selectBatchIds(Arrays.asList(ids.remove(0), ids.remove(0)));
        log.info("------------------------------------------------selectByMap--------------------------------------------------------");
        null2Mapper.selectByMap(Maps.newHashMap("id", ids.remove(0)));
        log.info("------------------------------------------------selectOne--------------------------------------------------------");
        null2Mapper.selectOne(Wrappers.<Null2>query().eq("id", ids.remove(0)));
        log.info("------------------------------------------------selectCount--------------------------------------------------------");
        null2Mapper.selectCount(Wrappers.<Null2>query().eq("id", ids.remove(0)));
        log.info("------------------------------------------------selectList--------------------------------------------------------");
        null2Mapper.selectList(Wrappers.<Null2>query().eq("id", ids.remove(0)));
        log.info("------------------------------------------------selectMaps--------------------------------------------------------");
        null2Mapper.selectMaps(Wrappers.<Null2>query().eq("id", ids.remove(0)));
        log.info("------------------------------------------------selectObjs--------------------------------------------------------");
        null2Mapper.selectObjs(Wrappers.<Null2>query().select("id").eq("id", ids.remove(0)));
        log.info("------------------------------------------------selectPage--------------------------------------------------------");
        null2Mapper.selectPage(new Page<>(), Wrappers.<Null2>query().eq("id", ids.remove(0)));
        log.info("------------------------------------------------selectMapsPage--------------------------------------------------------");
        null2Mapper.selectMapsPage(new Page<>(), Wrappers.<Null2>query().eq("id", ids.remove(0)));
    }
}
