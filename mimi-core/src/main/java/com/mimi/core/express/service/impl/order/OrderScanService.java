package com.mimi.core.express.service.impl.order;

import com.mimi.core.express.entity.order.OrderScan;
import com.mimi.core.express.mapper.order.OrderScanMapper;
import org.springframework.stereotype.Service;

@Service
public class OrderScanService extends BaseOrderService<OrderScanMapper, OrderScan> {

    @Override
    public String type() {
        return "派件扫描";
    }
}
