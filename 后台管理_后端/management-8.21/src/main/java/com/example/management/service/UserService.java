package com.example.management.service;


import com.example.management.pojo.User;
import com.example.management.vo.UserVo;

/**
 * @author 14158
 */
public interface UserService {

    /**
     * 查找或者建立新用户
     * @param openid openid
     * @param sessionKey sessionKey
     * @return User
     */
    User findOrCreateUser(String openid, String sessionKey);
    /**
     * 通过id查找用户
     * @param id 用户的id
     * @return userVo
     */
    UserVo findUserVoById(Long id);

    /**
     * 通过id查找用户
     * @param id 用户的id
     * @return user
     */
    User findUserById(Long id);

}
