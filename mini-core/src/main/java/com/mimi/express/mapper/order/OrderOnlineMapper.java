package com.mimi.express.mapper.order;

import com.mimi.express.entity.order.OrderOnline;
import com.mimi.express.entity.order.param.OrderOnlineParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderOnlineMapper extends OrderMapper<OrderOnline, OrderOnlineParam>{
    @Select({
            "SELECT * FROM t_order_online",
            BASE_CONDITION
    })
    @Override
    public List<OrderOnline> findPage(OrderOnlineParam param);

    @Select({"SELECT count(0) FROM t_order_online",
            BASE_CONDITION})
    @Override
    public long findCount(OrderOnlineParam param);
}
