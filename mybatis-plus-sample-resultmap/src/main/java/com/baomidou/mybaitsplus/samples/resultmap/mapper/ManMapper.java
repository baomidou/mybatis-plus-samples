package com.baomidou.mybaitsplus.samples.resultmap.mapper;

import com.baomidou.mybaitsplus.samples.resultmap.entity.Man;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author miemie
 * @since 2019-11-27
 */
public interface ManMapper extends BaseMapper<Man> {

    Man selectLinkById(Long id);
}
