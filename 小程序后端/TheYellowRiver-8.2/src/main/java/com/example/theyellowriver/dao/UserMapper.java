package com.example.theyellowriver.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.theyellowriver.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author 14158
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 通过openid找用户
     * @param openid 用户唯一标识
     * @return 用户
     */
    @Select("SELECT * FROM `user` WHERE openid = #{openid}")
    User findByOpenid(String openid);

}