package com.example.theyellowriver.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author 14158
 */
@Data
@TableName("user")
public class User {
    private Long id;
    private String sessionKey;
    private String openid;
    private String nickname;
    private String avatarUrl;
    private Long createDate;
    private Long lastLogin;
    private Integer gender;
    private String country;
    private String province;
    private String city;

}
