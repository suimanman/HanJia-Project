package com.example.hanjiaprojectbackend.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Component
public class JwtUtils {
    @Value("${spring.security.jwt.key}")//配置秘钥
    String key;
    @Value("${spring.security.jwt.expire}")//到期时间
    int expire;
    @Resource
    StringRedisTemplate template;//注入Redis
    //判断token失效方法
    public boolean invalidateJwt(String headerToken){
        String token = this.convertToken(headerToken);
        if (token == null) return false;
        Algorithm algorithm = Algorithm.HMAC256(key);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();//验证token
        try {
            DecodedJWT jwt = jwtVerifier.verify(token);
            String id=jwt.getId();
            return deleteToken(id,jwt.getExpiresAt());
        }catch (JWTVerificationException e){
            return false;
        }
    }
    private boolean isInvalidToken(String uuid){
        return Boolean.TRUE.equals(template.hasKey(Const.JWT_BLACK_LIST + uuid));
    }
    private boolean deleteToken(String uuid,Date time){
        if(this.isInvalidToken(uuid))
            return false;
        Date now=new Date();
        long expire= Math.max(0,time.getTime()-now.getTime());//防止过期相减变成负值
        template.opsForValue().set(Const.JWT_BLACK_LIST+uuid,"");
        return true;
    }
    //解码JWT
    public DecodedJWT resolveJwt(String headerToken) {
        String token = this.convertToken(headerToken);
        if (token == null) return null;
        Algorithm algorithm = Algorithm.HMAC256(key);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();//验证token
        try {
            DecodedJWT verify = jwtVerifier.verify(token);
            if(isInvalidToken(verify.getId()))
                return null;
            Date expire=verify.getExpiresAt();
            return new Date().after(expire) ? null:verify;//验证时间是否过期
        }catch (JWTVerificationException e){
            return null;//验证失败抛出运行时异常，返回空
        }
    }
    public String createJwt(UserDetails details,int id,String username){
        Algorithm algorithm=Algorithm.HMAC256(key);
        Date expire=this.expireTime();
        return JWT.create()
                .withJWTId(UUID.randomUUID().toString())//每个令牌都有一个随机的UUID，在验证token过期时，将令牌所对应UUID拉黑
                .withClaim("id",id)
                .withClaim("name",username)
                .withClaim("authorities",details.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())//获取权限
                .withExpiresAt(expire)//设置到期时间
                .withIssuedAt(new Date())//Token颁发时间
                .sign(algorithm);//令牌签名
    }
    public Date expireTime(){//计算到期时间
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.HOUR,expire * 24);
        return calendar.getTime();
    }
    public UserDetails toUser(DecodedJWT jwt){//封装成UserDetails类型
        Map<String , Claim> claims=jwt.getClaims();
        return User
                .withUsername(claims.get("name").asString())
                .password("**********")
                .authorities(claims.get("authorities").asArray(String.class))
                .build();
    }

    private String convertToken(String headerToken){//获取token
        if(headerToken == null || !headerToken.startsWith("Bearer "))//Token为空或者不是以Bearer 开头，则返回空
            return null;
        return headerToken.substring(7);//token正确，把“Bearer ”去掉，然后返回
    }
}
