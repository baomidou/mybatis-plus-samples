package com.baomidou.mybaitsplus.samples.resultmap.mapper;

import com.baomidou.mybaitsplus.samples.resultmap.entity.Woman;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author miemie
 * @since 2019-11-27
 */
public interface WomanMapper extends BaseMapper<Woman> {

    Woman selectLinkById(Long id);
}
