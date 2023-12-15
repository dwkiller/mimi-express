package com.mimi.express.entity.order.param;

import com.mimi.express.entity.order.OrderOut;
import lombok.Data;

@Data
public class OrderOutParam extends OrderOut implements BaseOrderParam {
    private int pageNum;
    private int pageSize;
}
