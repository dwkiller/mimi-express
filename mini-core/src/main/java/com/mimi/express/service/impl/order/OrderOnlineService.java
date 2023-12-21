package com.mimi.express.service.impl.order;

import com.mimi.express.entity.order.OrderOnline;
import com.mimi.express.mapper.order.OrderOnlineMapper;
import org.springframework.stereotype.Service;

@Service
public class OrderOnlineService extends BaseOrderService<OrderOnlineMapper, OrderOnline> {
    @Override
    public String type() {
        return "在线寄件";
    }
}
