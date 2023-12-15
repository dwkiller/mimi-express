package com.mimi.express.entity.order.param;

import com.mimi.express.entity.order.OrderScan;
import lombok.Data;

@Data
public class OrderScanParam extends OrderScan implements BaseOrderParam {
    private int pageNum;
    private int pageSize;
}
