package com.baomidou.mybatisplus.samples.sharding.config;

import com.baomidou.mybatisplus.extension.plugins.datasource.AbstractShardingStrategy;

import java.util.List;

public class IdShardingStrategy extends AbstractShardingStrategy {

    @Override
    public String chooseKey(List<String> keys, String statementId, Object parameter) {
        long id = parameter instanceof Number ? ((Number) parameter).longValue() : 0L;
        return keys.get((int) (Math.abs(id) % keys.size()));
    }
}
