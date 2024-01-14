package com.mimi.vo;

import lombok.Data;

@Data
public class TokenVo {
    private String token;
    private String openId;
    private int expiresIn;
}
