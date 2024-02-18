package com.example.hanjiaprojectbackend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI springDocOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("图书管理系统 - 在线API接口文档")   //设置API文档网站标题
                .description("这是一个图书管理系统的后端API文档，欢迎前端人员查阅！") //网站介绍
                .version("2.0")   //当前API版本
                .license(new License().name("我的B站个人主页")  //遵循的协议，这里拿来写其他的也行
                        .url("https://space.bilibili.com/37737161")));
    }
}
