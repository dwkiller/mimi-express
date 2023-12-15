package com.mimi.express.entity.order.param;

import com.mimi.express.entity.order.OrderAgent;
import lombok.Data;

@Data
public class OrderAgentParam extends OrderAgent implements BaseOrderParam{
    private int pageNum;
    private int pageSize;
}
