package com.example.management.controller;

import com.example.management.dao.UserMapper;
import com.example.management.pojo.User;
import com.example.management.pojo.UserVo;
import com.example.management.query.Id;
import com.example.management.query.SearchUser;
import com.example.management.vo.Result;
import jakarta.annotation.Resource;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * @author 14158
 */
@RestController
@CrossOrigin(origins = "http://localhost:9528")
@RequestMapping("/prod-api/api/users")
public class UserController {
    @Resource
    private UserMapper userMapper;
    @CrossOrigin(origins = "http://localhost:9528")
    @GetMapping("userData")
    public Result findAll(){
        List<User> users = userMapper.userList();
        return Result.success(copyList(users));
    }
    private List<UserVo> copyList(List<User> records) {
        List<UserVo> userVoList = new ArrayList<>();
        for (User record : records) {
            userVoList.add(copy(record));
        }
        return userVoList;
    }
    private UserVo copy(User user){
        UserVo userVo = new UserVo();
        //copy一下，把文章的东西copy到文章vo里
        try{
            BeanUtils.copyProperties(user,userVo);
        }catch (Exception e){
            System.out.println("错误原因"+e);
        }
        //设置一下时间
        userVo.setCreateDate(new DateTime(user.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        userVo.setLastLogin(new DateTime(user.getLastLogin()).toString("yyyy-MM-dd HH:mm"));
        return userVo;
    }

    @PostMapping("searchUserById")
    public Result searchUserById(@RequestBody SearchUser searchUser){
        if (isEmpty(userMapper.selectById(searchUser.getId()))){
            return Result.fail(50001,"未找到该用户");
        }
        return Result.success(userMapper.selectById(searchUser.getId()));
    }
    @PostMapping("searchUserByNickName")
    public Result searchUserByNickName(@RequestBody SearchUser searchUser){
        if (isEmpty(userMapper.selectByNickName(searchUser.getNickName()))){
            return Result.fail(50001,"未找到该用户");
        }
        return Result.success(userMapper.selectByNickName(searchUser.getNickName()));
    }
    @PostMapping("deleteUser")
    public Result deleteUser(@RequestBody Id id){
        if (isEmpty(userMapper.selectById(id.getId()))){
            return Result.fail(50001,"未找到该用户");
        }
        userMapper.deleteById(id.getId());
        return Result.success(null);
    }
}
