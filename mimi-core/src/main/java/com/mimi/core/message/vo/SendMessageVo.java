package com.mimi.core.message.vo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.Map;

@Data
public class SendMessageVo {

    private String templateId;
    private JSONObject order;
    private Map<String,String> param;
}
