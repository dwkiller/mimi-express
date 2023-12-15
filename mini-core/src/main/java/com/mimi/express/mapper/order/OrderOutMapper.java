package com.mimi.express.mapper.order;

import com.mimi.express.entity.order.OrderOut;
import com.mimi.express.entity.order.param.OrderOutParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderOutMapper extends OrderMapper<OrderOut, OrderOutParam>{
    @Select({
            "SELECT * FROM t_order_out",
            BASE_CONDITION
    })
    @Override
    public List<OrderOut> findPage(OrderOutParam param);

    @Select({"SELECT count(0) FROM t_order_out",
            BASE_CONDITION})
    @Override
    public long findCount(OrderOutParam param);


}
