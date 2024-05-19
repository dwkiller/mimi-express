package com.mimi.vo;

import com.mimi.core.express.entity.order.OrderIn;
import lombok.Data;

@Data
public class OrderInStateVo extends OrderIn{

    private String state;

    private String outUser;

    private String outMobile;

    private String agentUser;

    private String agentMobile;

}
