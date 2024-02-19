package com.example.hanjiaprojectbackend.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.hanjiaprojectbackend.utils.JwtUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component//验证token的过滤器，加到security的filterChain中
public class JwtAuthorizeFilter extends OncePerRequestFilter {

    @Resource
    JwtUtils utils;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorization=request.getHeader("Authorization");
        DecodedJWT jwt= utils.resolveJwt(authorization);
        if(jwt != null){
            UserDetails user=utils.toUser(jwt);
            UsernamePasswordAuthenticationToken authentication=
                    new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);//把验证信息丢进去，表明jwt验证通过
        }
        filterChain.doFilter(request,response);
    }
}
