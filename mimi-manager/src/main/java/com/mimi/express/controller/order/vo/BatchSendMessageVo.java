package com.mimi.express.controller.order.vo;

import lombok.Data;

import java.util.List;

@Data
public class BatchSendMessageVo {
    private String templateId;
    private List<BatchMsgBodyVo> orderParam;
}
