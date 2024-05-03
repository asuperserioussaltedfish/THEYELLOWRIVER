package com.example.management.service.impl;

import com.example.management.dao.UserMapper;
import com.example.management.pojo.User;
import com.example.management.pojo.UserInfo;
import com.example.management.service.UserService;
import com.example.management.vo.UserVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;


/**
 * @author 14158
 */
@Service
public class UserServiceImpl implements UserService {
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
        newUser.setAvatarUrl("dibao.PNG");
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
            sysUser.setAvatarUrl("dibao.PNG");
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





}
