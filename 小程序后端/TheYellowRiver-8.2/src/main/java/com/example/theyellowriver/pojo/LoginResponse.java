package com.example.theyellowriver.pojo;

import lombok.Data;

/**
 * @author 14158
 */
@Data
public class LoginResponse {
    private String token;
    private User user;

    public LoginResponse(String token, User user) {
        this.token = token;
        this.user = user;
    }

}