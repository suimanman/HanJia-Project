package com.example.hanjiaprojectbackend;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Slf4j
@SpringBootTest
class HanjiaProjectBackendApplicationTests {
    //JavaMailSender是专门用于发送邮件的对象，自动配置类已经提供了Bean
    @Resource
    JavaMailSender sender;
    @Test
    void contextLoads() {
        //SimpleMailMessage是一个比较简易的邮件封装，支持设置一些比较简单内容
        SimpleMailMessage message = new SimpleMailMessage();
        //设置邮件标题
        message.setSubject("邮件题目");
        //设置邮件内容
        message.setText("邮件内容");
        //设置邮件发送给谁，可以多个，这里就发给你的QQ邮箱
        message.setTo("1847515809@qq.com");
        //邮件发送者，这里要与配置文件中的保持一致
        message.setFrom("wmc19831983050@126.com");
        //OK，万事俱备只欠发送
        sender.send(message);
        System.out.println("发送成功");
    }

}
