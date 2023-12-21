package com.mimi.express.service.impl.order;

import com.mimi.express.entity.order.OrderIn;
import com.mimi.express.mapper.order.OrderInMapper;
import org.springframework.stereotype.Service;

@Service
public class OrderInService extends BaseOrderService<OrderInMapper, OrderIn> {
    @Override
    public String type() {
        return "入库单";
    }
}
