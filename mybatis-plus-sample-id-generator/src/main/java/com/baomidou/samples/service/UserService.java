package com.baomidou.samples.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.samples.entity.User;
import com.baomidou.samples.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 * @author sundongkai 2021/1/12
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> {

}
