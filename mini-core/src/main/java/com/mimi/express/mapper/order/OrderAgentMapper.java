package com.mimi.express.mapper.order;

import com.mimi.express.entity.order.OrderAgent;
import com.mimi.express.entity.order.param.OrderParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderAgentMapper extends OrderMapper<OrderAgent>{
    @Select({"<script>",
            "SELECT * FROM t_order_agent",
            BASE_CONDITION,
            "</script>"
    })

    @Override
    public List<OrderAgent> findPage(OrderParam<OrderAgent> param);

    @Select({"<script>","SELECT count(0) FROM t_order_agent",
            BASE_CONDITION,
            "</script>"})
    @Override
    public long findCount(OrderParam<OrderAgent> param);
}
