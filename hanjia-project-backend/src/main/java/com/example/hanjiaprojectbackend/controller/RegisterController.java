package com.example.hanjiaprojectbackend.controller;

import com.example.hanjiaprojectbackend.entity.User;
import com.example.hanjiaprojectbackend.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Random;
@RestController
//使用@Tag注解来添加Controller描述信息
@Tag(name = "账户验证相关", description = "包括用户登录、注册、验证码请求等操作。")
public class RegisterController {
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    UserMapper mapper;
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "测试成功"),
            @ApiResponse(responseCode = "500", description = "测试失败")   //不同返回状态码描述
    })
    @Operation(summary = "获取验证码",description = "填取email地址，获取验证码")
    @PostMapping("/code")
    public User getCode(@Parameter(description = "邮件地址",example = "1847515809@qq.com") @RequestParam String email,
            HttpSession session){
        Random random=new Random();
        int code= random.nextInt(900000)+100000;
        session.setAttribute("code",code);
        session.setAttribute("email",email);
        SimpleMailMessage message=new SimpleMailMessage();
        message.setSubject("你的验证码");
        message.setText("验证码是："+code+"，有效时间3分钟，请妥善保存！");
        message.setTo(email);
        message.setFrom("wmc19831983050@126.com");
        mailSender.send(message);
        System.out.println(message);
        return new User(1,"wadwa","weda","wew");
    }
    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String email,
                           @RequestParam int code,
                           HttpSession session){
        String sessionEmail=(String) session.getAttribute("email");
        Integer sessionCode=(Integer) session.getAttribute("code");
        System.out.println(sessionCode);
        System.out.println(code);
        if(sessionCode==null) return "请先获取验证码！";
        if(!sessionEmail.equals(email)) return "请先获取验证码";
        if(sessionCode!=code) return "验证码错误！";
        mapper.createUser(username,password,email);
        return "/index";

    }
}
