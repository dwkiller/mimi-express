package com.mimi.express.mapper.order;

import com.mimi.express.entity.order.OrderScan;
import com.mimi.express.entity.order.param.OrderScanParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderScanMapper extends OrderMapper<OrderScan, OrderScanParam>{
    @Select({
            "SELECT * FROM t_order_scan",
            BASE_CONDITION
    })
    @Override
    public List<OrderScan> findPage(OrderScanParam param);

    @Select({"SELECT count(0) FROM t_order_scan",
            BASE_CONDITION})
    @Override
    public long findCount(OrderScanParam param);
}
