package com.mimi.express.entity.order.param;

import com.mimi.express.entity.order.OrderIn;
import lombok.Data;

@Data
public class OrderInParam extends OrderIn implements BaseOrderParam {
    private int pageNum;
    private int pageSize;
}
