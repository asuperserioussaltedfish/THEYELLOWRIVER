package com.example.management.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @author 14158
 */
@Data
@TableName("user")
public class UserVo {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String sessionKey;
    private String openid;
    private String nickname;
    private String avatarUrl;
    private String createDate;
    private String lastLogin;
    private Integer gender;
    private String country;
    private String province;
    private String city;

}
