package com.example.theyellowriver.utils;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 14158
 */
public class JwtUtil {

    private static final String JWT_TOKEN = "123456saltedFish!@###$$";

    /**
     * 生成token
     * @param userId userid
     * @return 字符串
     */
    public static String createToken(Long userId){
        Map<String,Object> claims = new HashMap<>(1);
        claims.put("userId",userId);
        JwtBuilder jwtBuilder = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, JWT_TOKEN)
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60  * 1000));
        return jwtBuilder.compact();
    }

    public static Map<String, Object> checkToken(String token){
        try {
            //解析token
            return (Map<String, Object>) Jwts.parser().setSigningKey(JWT_TOKEN).parse(token).getBody();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }
}
