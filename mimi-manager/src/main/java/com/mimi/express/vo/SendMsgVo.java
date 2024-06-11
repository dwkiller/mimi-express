package com.mimi.express.vo;

import lombok.Data;

import java.util.Map;

@Data
public class SendMsgVo {

    private String templateId;
    private Map<String,String> sendParam;
}
