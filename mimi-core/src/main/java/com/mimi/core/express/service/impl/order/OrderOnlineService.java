package com.mimi.core.express.service.impl.order;

import com.mimi.core.express.entity.order.OrderOnline;
import com.mimi.core.express.mapper.order.OrderOnlineMapper;
import org.springframework.stereotype.Service;

@Service
public class OrderOnlineService extends BaseOrderService<OrderOnlineMapper, OrderOnline> {
    @Override
    public String type() {
        return "在线寄件";
    }
}
