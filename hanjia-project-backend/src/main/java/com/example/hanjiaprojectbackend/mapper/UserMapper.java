package com.example.hanjiaprojectbackend.mapper;

import com.example.hanjiaprojectbackend.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Insert("insert into user(username,password,email) values(#{username},#{password},#{email})")
    public void createUser(String username,String password,String email);
}
