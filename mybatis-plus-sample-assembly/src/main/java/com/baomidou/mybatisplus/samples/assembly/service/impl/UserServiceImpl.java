package com.baomidou.mybatisplus.samples.assembly.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.samples.assembly.entity.User;
import com.baomidou.mybatisplus.samples.assembly.mapper.UserMapper;
import com.baomidou.mybatisplus.samples.assembly.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * @author nieqiuqiu
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
