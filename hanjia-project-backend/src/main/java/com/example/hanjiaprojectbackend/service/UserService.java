package com.example.hanjiaprojectbackend.service;

import com.example.hanjiaprojectbackend.entity.User;
import com.example.hanjiaprojectbackend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;


}
