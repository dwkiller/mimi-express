package com.mimi.express.service.impl.order;

import com.mimi.express.entity.order.OrderAgent;
import com.mimi.express.entity.order.param.OrderAgentParam;
import com.mimi.express.mapper.order.OrderAgentMapper;
import org.springframework.stereotype.Service;

@Service
public class OrderAgentService extends BaseOrderService<OrderAgentMapper,
        OrderAgent, OrderAgentParam>{

}
