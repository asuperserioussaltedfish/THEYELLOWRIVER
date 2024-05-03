package com.example.theyellowriver.pojo;

import lombok.Data;

/**
 * @author 14158
 */
@Data
public class OpenIdAndSessionKey {
    private String openId;
    private String sessionKey;

    public OpenIdAndSessionKey(String openId, String sessionKey){
        this.sessionKey=sessionKey;
        this.openId=openId;
    }
}
