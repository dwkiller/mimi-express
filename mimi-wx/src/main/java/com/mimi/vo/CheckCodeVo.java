package com.mimi.vo;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class CheckCodeVo {
    @Pattern(regexp = "^1[3-9]\\d{9}$",message = "请输入正确的手机号!")
    private String mobile;
}
