package com.mimi.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class TokenVo implements Serializable {
    private String token;
    private String openId;
    private int expiresIn;
    private String schoolId;
    private String userId;
    private String phone;
    private String realName;
}
