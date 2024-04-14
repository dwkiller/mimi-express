package com.mimi.express.controller.order.vo;

import com.alibaba.fastjson.JSONObject;
import com.mimi.core.express.entity.order.BaseOrder;
import lombok.Data;

import java.util.Map;

@Data
public class BatchMsgBodyVo {
    private JSONObject order;
    private Map<String,String> param;
}
