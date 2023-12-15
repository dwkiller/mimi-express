package com.mimi.express.mapper.order;

import com.mimi.express.entity.order.OrderAgent;
import com.mimi.express.entity.order.param.OrderAgentParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderAgentMapper extends OrderMapper<OrderAgent, OrderAgentParam>{
    @Select({
            "SELECT * FROM t_order_agent",
            BASE_CONDITION,DONE_CONDITION,EXPRESS_DELIVERY_CONDITION
    })
    @Override
    public List<OrderAgent> findPage(OrderAgentParam param);

    @Select({"SELECT count(0) FROM t_order_agent",
            BASE_CONDITION,DONE_CONDITION,EXPRESS_DELIVERY_CONDITION})
    @Override
    public long findCount(OrderAgentParam param);
}
