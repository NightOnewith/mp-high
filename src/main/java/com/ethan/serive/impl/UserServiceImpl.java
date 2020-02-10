package com.ethan.serive.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ethan.dao.UserMapper;
import com.ethan.entity.User;
import com.ethan.serive.UserService;
import org.springframework.stereotype.Service;

/**
 * Created by ASUS on 2020/2/4.
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
