package com.baomidou.mybatisplus.samples.resultmap;

import com.baomidou.mybatisplus.samples.resultmap.entity.Child;
import com.baomidou.mybatisplus.samples.resultmap.entity.Man;
import com.baomidou.mybatisplus.samples.resultmap.entity.Woman;
import com.baomidou.mybatisplus.samples.resultmap.mapper.ChildMapper;
import com.baomidou.mybatisplus.samples.resultmap.mapper.ManMapper;
import com.baomidou.mybatisplus.samples.resultmap.mapper.WomanMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author miemie
 * @since 2019-11-27
 */
@Slf4j
@SpringBootTest
class ResultmapTest {
    @Autowired
    private ChildMapper childMapper;
    @Autowired
    private ManMapper manMapper;
    @Autowired
    private WomanMapper womanMapper;

    @Test
    void t_c() {
        final Child child = childMapper.selectLinkById(1L);
        log.info("child: {}", child);
        assertThat(child).isNotNull();
        final Man laoHan = child.getLaoHan();
        assertThat(laoHan).isNotNull();
        assertThat(laoHan.getName()).isNotBlank();
        final Woman laoMa = child.getLaoMa();
        assertThat(laoMa).isNotNull();
        assertThat(laoMa.getName()).isNotBlank();
    }

    @Test
    void t_m() {
        final Man man = manMapper.selectLinkById(1L);
        log.info("man: {}", man);
        assertThat(man).isNotNull();
        assertThat(man.getName()).isNotBlank();
        final Woman laoPo = man.getLaoPo();
        assertThat(laoPo).isNotNull();
        assertThat(laoPo.getName()).isNotBlank();
        final List<Child> waWa = man.getWaWa();
        assertThat(waWa).isNotEmpty();
        waWa.forEach(i -> assertThat(i.getName()).isNotBlank());
    }

    @Test
    void t_w() {
        final Woman woman = womanMapper.selectLinkById(1L);
        log.info("woman: {}", woman);
        assertThat(woman).isNotNull();
        assertThat(woman.getName()).isNotBlank();
        final Man laoGong = woman.getLaoGong();
        assertThat(laoGong).isNotNull();
        assertThat(laoGong.getName()).isNotBlank();
        final List<Child> waWa = woman.getWaWa();
        assertThat(waWa).isNotEmpty();
        waWa.forEach(i -> assertThat(i.getName()).isNotBlank());
    }
}
