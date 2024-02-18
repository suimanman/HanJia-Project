package com.example.hanjiaprojectbackend.config;

import com.example.hanjiaprojectbackend.entity.RestBean;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(conf->conf
                        .requestMatchers("/api/auth/**").permitAll()//配置允许通过的接口
                        .anyRequest().authenticated())//其他接口均需要验证
                .formLogin(conf->conf//登录验证
                        .loginProcessingUrl("/api/auth/login")
                        .successHandler(this::onAuthenticationSuccess)//成功返回
                        .failureHandler(this::onAuthenticationFailure)
                )
                .logout(conf->conf//退出验证
                        .logoutUrl("/api/auth/logout")
                        .logoutSuccessHandler(this::onLogoutSuccess)
                )
                .csrf(AbstractHttpConfigurer::disable)//关闭csrf
                .sessionManagement(conf->conf.//将session设置为无状态，让security不去处理session
                        sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");//配置编码格式，不然中文返回值会乱码
        response.getWriter().write(RestBean.success().asJsonString());
    }
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");//配置编码格式，不然中文返回值会乱码
        response.getWriter().write(RestBean.failure(401, exception.getMessage()).asJsonString());
    }
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {

    }
}
