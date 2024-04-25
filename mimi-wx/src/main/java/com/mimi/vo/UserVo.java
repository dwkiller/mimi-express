package com.mimi.vo;

import com.mimi.core.express.entity.user.User;
import lombok.Data;

@Data
public class UserVo {

    private User user;
    private TokenVo tokenVo;
    private String checkCode;
}
