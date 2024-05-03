package com.example.management.controller;

import com.alibaba.fastjson2.JSON;
import com.example.management.pojo.Data;
import com.example.management.utils.JwtUtil;
import com.example.management.vo.ErrorCode;
import com.example.management.vo.LoginRequest;
import com.example.management.vo.Result;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;


/**
 * @author 14158
 */
@RestController
@RequestMapping("/prod-api")
public class LoginController {
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @CrossOrigin(origins = "http://localhost:9528")
    @PostMapping("/login")
    public Result login(@RequestBody LoginRequest loginRequest) {
        String account="admin";
        System.out.println(account.equals(loginRequest.getAccount()));
        System.out.println(account.equals(loginRequest.getPassword()));
        if (account.equals(loginRequest.getAccount())&&account.equals(loginRequest.getPassword())){
            String token = JwtUtil.createToken(1415L);
            //保存到Redis中
            redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(1415),
                    1, TimeUnit.DAYS);

            return Result.success(token);
        }
        else{
            return Result.fail(ErrorCode.USER_LOGIN_ERROR.getCode(),ErrorCode.USER_LOGIN_ERROR.getMsg());
        }
    }
    @CrossOrigin(origins = "http://localhost:9528")
    @GetMapping("/logout")
    public Result logout(@RequestHeader("Authorization") String token){
        redisTemplate.delete("TOKEN_"+token);
        return Result.success(null);
    }
}
