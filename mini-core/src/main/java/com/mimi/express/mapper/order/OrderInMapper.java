package com.mimi.express.mapper.order;

import com.mimi.express.entity.order.OrderIn;
import com.mimi.express.entity.order.param.OrderInParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderInMapper extends OrderMapper<OrderIn, OrderInParam>{
    @Select({
            "SELECT * FROM t_order_in",
            BASE_CONDITION,EXPRESS_DELIVERY_CONDITION
    })
    @Override
    public List<OrderIn> findPage(OrderInParam param);

    @Select({"SELECT count(0) FROM t_order_in",
            BASE_CONDITION,EXPRESS_DELIVERY_CONDITION})
    @Override
    public long findCount(OrderInParam param);
}
