package com.mimi.core.express.service.impl.order;

import com.mimi.core.express.entity.order.OrderOut;
import com.mimi.core.express.mapper.order.OrderOutMapper;
import org.springframework.stereotype.Service;

@Service
public class OrderOutService extends BaseOrderService<OrderOutMapper, OrderOut> {
    @Override
    public String type() {
        return "出库单";
    }
}
