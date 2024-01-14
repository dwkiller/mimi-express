package com.mimi.core.express.service.impl.order;

import com.mimi.core.express.entity.order.OrderIn;
import com.mimi.core.express.mapper.order.OrderInMapper;
import org.springframework.stereotype.Service;

@Service
public class OrderInService extends BaseOrderService<OrderInMapper, OrderIn> {
    @Override
    public String type() {
        return "入库单";
    }
}
