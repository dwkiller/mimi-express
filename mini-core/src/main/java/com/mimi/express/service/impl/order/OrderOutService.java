package com.mimi.express.service.impl.order;

import com.mimi.express.entity.order.OrderOut;
import com.mimi.express.entity.order.param.OrderOutParam;
import com.mimi.express.mapper.order.OrderOutMapper;
import org.springframework.stereotype.Service;

@Service
public class OrderOutService extends BaseOrderService<OrderOutMapper, OrderOut, OrderOutParam> {
}
