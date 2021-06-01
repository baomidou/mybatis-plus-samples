package com.baomidou.mybatisplus.samples.association.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.samples.association.entity.User;
import com.baomidou.mybatisplus.samples.association.mapper.UserMapper;
import com.baomidou.mybatisplus.samples.association.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public List<User> selectUserPage(IPage<User> page, QueryWrapper<User> wrapper) {
        return baseMapper.selectUserPage(page, wrapper);
    }
}
