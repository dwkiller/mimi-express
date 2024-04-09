package com.mimi.vo;

import lombok.Data;

@Data
public class WxConfigVo {
    private String appId;
    private String timeStamp;

    private String nonceStr;

    private String signature;
}
