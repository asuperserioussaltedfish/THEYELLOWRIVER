package com.example.theyellowriver.controller;

import com.alibaba.fastjson2.JSON;
import com.example.theyellowriver.dao.UserMapper;
import com.example.theyellowriver.handler.Fish;
import com.example.theyellowriver.pojo.*;
import com.example.theyellowriver.service.ArticleService;
import com.example.theyellowriver.service.OssService;
import com.example.theyellowriver.service.UserService;
import com.example.theyellowriver.utils.JwtUtil;
import com.example.theyellowriver.utils.UserThreadLocal;
import com.example.theyellowriver.vo.Result;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.TimeUnit;


/**总体思路：
 * 用户发起登录请求，向服务器传递临时登录凭证和用户信息。
 * 服务器调用微信登录API，获取openid和session_key。
 * 如果用户已登录，则直接返回用户的openid和session_key。
 * 如果用户未登录，则发送请求到微信登录API，获取openid和session_key。
 * 服务器根据openid查找或创建用户。
 * 客户端存储token，用于后续的请求。
 * 用户发起登出请求，服务器删除存储在Redis中的token。
 * @author 14158
 */

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Resource
    private OssService ossService;
    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private UserService userService;
    /**
     * 登录
     * @param loginRequest 登录需要的参数
     *String code（临时登录凭证）
     *下面是userInfo(用户的信息)
     *String nickname;（用户名称）
     *String avatarUrl;（头像的url）
     *Integer gender;（性别，1是男，0是女）
     * Fish注解：登录放行
     * @return 返回
     */
    @Fish
    @PostMapping("/login")
    public Result login(@RequestBody LoginRequest loginRequest) {
        //获取登录凭证
        String code = loginRequest.getCode();
        //获取userinfo
        UserInfo userInfo = loginRequest.getUserInfo();
        //请求微信服务器换取openid
        OpenIdAndSessionKey openIdAndSessionKey = userService.login(code, userInfo);
        String openid = openIdAndSessionKey.getOpenId();
        String sessionKey=openIdAndSessionKey.getSessionKey();
        User user = userService.findOrCreateUser(openid,
                sessionKey);


        if (user==null){
            User user1 = new User();
            user1.setOpenid(openid);
            user1.setNickname("微信用户");
            user1.setSessionKey(sessionKey);
            user1.setCreateDate(System.currentTimeMillis());
            user1.setLastLogin(System.currentTimeMillis());
            user1.setAvatarUrl("图片地址");
            userMapper.insert(user1);
            String token = JwtUtil.createToken(user1.getId());
            //保存到Redis中
            redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(user1),
                    1, TimeUnit.DAYS);
            return Result.success(new LoginResponse(token, user1));
        }
        String token = JwtUtil.createToken(user.getId());
        //保存到Redis中
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(user),
                1, TimeUnit.DAYS);

        return Result.success(new LoginResponse(token, user));
    }
    @GetMapping("/logout")
    public Result logout(@RequestHeader("Authorization") String token){
        redisTemplate.delete("TOKEN_"+token);
        return Result.success(null);
    }
    @GetMapping("currentUser")
    public Result currentUser(@RequestHeader("Authorization") String token){
        return userService.getUserInfoByToken(token);
    }
    @PostMapping("/modify")
    public Result modify(@RequestBody User userInfo){
        User user = UserThreadLocal.get();
        user.setNickname(userInfo.getNickname());
        user.setGender(userInfo.getGender());
        user.setProvince(user.getProvince());
        user.setCountry(user.getCountry());
        user.setCity(user.getCity());
        userMapper.updateById(user);
        return Result.success(user);
    }
    @PostMapping("/modifyAvatar")
    public Result modify(@RequestParam("image") MultipartFile avatar) throws Exception {
        User user = UserThreadLocal.get();
        user.setAvatarUrl(ossService.uploadFileAvatar(avatar));
        userMapper.updateById(user);
        return Result.success(user);
    }
}
