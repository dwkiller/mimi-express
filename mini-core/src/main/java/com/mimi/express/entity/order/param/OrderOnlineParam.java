package com.mimi.express.entity.order.param;

import com.mimi.express.entity.order.OrderOnline;
import lombok.Data;

@Data
public class OrderOnlineParam extends OrderOnline implements BaseOrderParam {
    private int pageNum;
    private int pageSize;
}
