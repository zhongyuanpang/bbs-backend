package com.pzy.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.pzy.dto.UserDto;
import io.jsonwebtoken.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * @Author Nice
 * @Date 2021/7/1 11:57
 */

/**
 * jwt工具类
 */
@Slf4j
@Data
@Component
public class JwtUtil {

    /**
     * 过期时间
     */
    public static final long EXPIRE = 1000 * 60 * 60 * 24;
    /**
     * 密钥
     */
    public static final String SECRET = "zjKkye4PN59B2wriTjtVCo3BOYoD1B";

    /**
     * 生成jwt token
     */
    public String generateToken(UserDto user) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setSubject("bbs_user") // 主题
                .claim("user", JSON.toJSONString(user))
                .setIssuedAt(new Date()) // 签发时间
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))  //过期时间
                .signWith(SignatureAlgorithm.HS256, SECRET) // 签名算法以及密匙
                .compact();
    }

    /**
     * @param token
     * @description: 解析jwt 转为userdto
     * @return: Claims
     * @author: 庞中原
     * @date: 2022/5/22 17:37
     */
    public UserDto getClaimByToken(String token) {
        try {
            Claims body = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
            Object user = body.get("user");
            Map params = JSONObject.parseObject(user.toString(), new TypeReference<>(){});
            UserDto userDto = MapTools.mapToEntity(params, UserDto.class);
            return userDto;
        } catch (Exception e) {
            log.debug("validate is token error ", e);
            return null;
        }
    }

    /**
     * token是否过期
     *
     * @return true：过期
     */
    public boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }
}
