package com.example.theyellowriver.vo;

import lombok.Data;

/**
 * @author 14158
 */
@Data
public class LoginUserVo {
    private Long id;

    private String openid;

    private String nickname;

    private String avatar;
private Integer auditStatus;
}
