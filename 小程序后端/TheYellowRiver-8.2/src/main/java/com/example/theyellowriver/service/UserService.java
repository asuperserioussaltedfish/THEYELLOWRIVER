package com.example.theyellowriver.service;

import com.example.theyellowriver.pojo.OpenIdAndSessionKey;
import com.example.theyellowriver.pojo.User;
import com.example.theyellowriver.pojo.UserInfo;
import com.example.theyellowriver.vo.Result;
import com.example.theyellowriver.vo.UserVo;

import java.util.List;

/**
 * @author 14158
 */
public interface UserService {
    /**
     * 通过token获取当前用户信息
     * @param token token
     * @return Result
     */
     Result getUserInfoByToken(String token) ;

    /**
     * 通过code与userInfo向微信服务器请求并返回openid和session_key
     * @param code 临时状态码
     * @param userInfo 用户信息
     * @return openid和session_key
     */
    OpenIdAndSessionKey login(String code, UserInfo userInfo);

    /**
     * 查找或者建立新用户
     * @param openid openid
     * @param sessionKey sessionKey
     * @return User
     */
    User findOrCreateUser(String openid,String sessionKey);
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
