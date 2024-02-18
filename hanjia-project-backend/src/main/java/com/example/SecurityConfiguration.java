//package com.example;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
////登录验证
//@Configuration
//@EnableWebSecurity
//public class SecurityConfiguration {
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http.
//                authorizeHttpRequests(auth->{
//                    auth.anyRequest().authenticated();
//                })
//                .formLogin((conf->{
//                    conf.loginPage("/login");
//                    conf.loginProcessingUrl("/dologin");
//                    conf.defaultSuccessUrl("/");
//                    conf.permitAll();
//                }))
//                .build();
//    }
//
//}
