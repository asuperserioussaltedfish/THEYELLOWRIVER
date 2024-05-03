package com.example.theyellowriver.pojo;

import lombok.Data;

/**
 * @author 14158
 */
@Data
public class LoginRequest {

    private String code;
    private UserInfo userInfo;



}