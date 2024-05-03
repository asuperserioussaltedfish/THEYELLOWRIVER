package com.example.theyellowriver.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.theyellowriver.config.WechatConfig;
import com.example.theyellowriver.dao.UserMapper;
import com.example.theyellowriver.pojo.OpenIdAndSessionKey;
import com.example.theyellowriver.pojo.User;
import com.example.theyellowriver.pojo.UserInfo;
import com.example.theyellowriver.service.UserService;
import com.example.theyellowriver.utils.JwtUtil;
import com.example.theyellowriver.vo.ErrorCode;
import com.example.theyellowriver.vo.LoginUserVo;
import com.example.theyellowriver.vo.Result;
import com.example.theyellowriver.vo.UserVo;
import jakarta.annotation.Resource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


/**
 * @author 14158
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private WechatConfig wechatConfig;
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private UserMapper userMapper;
    @Override
    public User findOrCreateUser(String openid,String sessionKey) {
        // 根据openid查找用户
        User user = null;
        try {
            user = userMapper.findByOpenid(openid);
        } catch (Exception e) {
            System.out.println("错误原因:" + e);
        }
        return user;
    }

    private User setUser(String sessionKey, User newUser, UserInfo userInfo1) {
        newUser.setNickname(userInfo1.getNickname());
        newUser.setAvatarUrl("");
        newUser.setGender(userInfo1.getGender());
        newUser.setCity(userInfo1.getCity());
        newUser.setCountry(userInfo1.getCountry());
        newUser.setProvince(userInfo1.getProvince());
        newUser.setSessionKey(sessionKey);
        newUser.setCreateDate(System.currentTimeMillis());
        newUser.setLastLogin(System.currentTimeMillis());
        userMapper.insert(newUser);
        return newUser;
    }

    @Override
    public UserVo findUserVoById(Long id) {
        User sysUser = userMapper.selectById(id);
        if (sysUser == null){
            sysUser = new User();
            sysUser.setId(1L);
            sysUser.setAvatarUrl("");
            sysUser.setNickname("微信用户");
        }
        UserVo userVo = new UserVo();
        userVo.setAvatar(sysUser.getAvatarUrl());
        userVo.setNickname(sysUser.getNickname());
        userVo.setId(sysUser.getId());
        return userVo;
    }

    @Override
    public User findUserById(Long id) {
        User sysUser=userMapper.selectById(id);
        //如果用户为空防止空指针异常加一个名字
        if (sysUser == null){
            sysUser=new User();
            sysUser.setNickname("微信用户");
        }
        return userMapper.selectById(id);
    }


    /**
     * 通过token获取当前用户
     * @param token token
     * @return Result
     */
    @Override
    public Result getUserInfoByToken(String token) {
        Map<String, Object> map = JwtUtil.checkToken(token);
        if (map == null){
            return Result.fail(ErrorCode.USER_NOT_LOGGED_IN.getCode(),ErrorCode.USER_NOT_LOGGED_IN.getMsg());
        }
        String userJson = (String) redisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isBlank(userJson)){
            return Result.fail(ErrorCode.USER_NOT_LOGGED_IN.getCode(), ErrorCode.USER_NOT_LOGGED_IN.getMsg());
        }
        User user = JSON.parseObject(userJson, User.class);
        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setOpenid(user.getOpenid());
        loginUserVo.setAvatar("");
        loginUserVo.setId(user.getId());
        loginUserVo.setNickname(user.getNickname());
        return Result.success(loginUserVo);
    }

    /**
     * 处理用户登录
     * @param code 临时状态码
     * @param userInfo 用户信息
     * @return OpenId和SessionKey
     */
    @Override
    public OpenIdAndSessionKey login(String code, UserInfo userInfo) {
        // 如果用户未登录，则发送请求到微信服务器获取openid和session_key
        String weChatLogin = "https://api.weixin.qq.com/sns/jscode2session?" +
                "appid={appId}&secret={secret}&js_code={code}&grant_type=authorization_code";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map<String, Object>> responseEntity = restTemplate
                .exchange(weChatLogin, HttpMethod.GET, entity,
                        new ParameterizedTypeReference<>() {
                        }, wechatConfig.getAppId(), wechatConfig.getSecret(), code);
        Map<String, Object> body = responseEntity.getBody();
        assert body != null;
        User user = userMapper.findByOpenid((String) body.get("openid"));
        // 如果用户已登录过，则直接返回用户的openid和session_key
        if (user != null) {
            return new OpenIdAndSessionKey(user.getOpenid(), user.getSessionKey());
        } else {
            //断定body不为空
            String openid = (String) body.get("openid");
            String sessionKey = (String) body.get("session_key");
            return new OpenIdAndSessionKey(openid, sessionKey);
        }
    }


}
