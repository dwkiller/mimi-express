package com.mimi.vo;

import lombok.Data;

@Data
public class PayReturnVo {

    private String timestamp;

    private String nonceStr;

    private String sign;

    private String signType;

    private String pkg;

    private String prePayId;
}
