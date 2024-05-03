package com.example.management.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.management.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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
    @Select("SELECT * FROM user WHERE openid = #{openid}")
    User findByOpenid(String openid);

    /**
     * 用户总数量
     * @return integer
     */
    @Select("select count(*) as allUser from user")
    Integer numberOfUser();
    /**
     * 当日新增用户
     * @return int
     */
    @Select("SELECT COUNT(*) AS new_user_count FROM user " +
            "WHERE DATE(FROM_UNIXTIME(round(create_date/1000,0))) = CURDATE()")
    Integer newUser();
    /**
     * 用户列表
     * @return List<User></>
     */
    @Select("select * from user order by create_date desc ")
    List<User> userList();
    /**      查找用户
     * @param name 名字
     * @return List<User></>
     */
    @Select("select * from user where nickname like concat('%',#{name},'%')")
    List<User> selectByNickName(String name);
}