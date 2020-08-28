package com.baomidou.mybatisplus.samples.pagination.service;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.samples.pagination.entity.User;
import com.baomidou.mybatisplus.samples.pagination.mapper.UserMapper;

/**
 * <p>
 * </p>
 *
 * @author yuxiaobin
 * @date 2020/7/2
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
