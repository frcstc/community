package com.hzgood.community.service;

import com.hzgood.community.mapper.UserMapper;
import com.hzgood.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User findByToken(String token) {
        return userMapper.findByToken(token);
    }
}
