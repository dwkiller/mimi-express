package com.mimi.express.mapper.order;

import com.mimi.express.entity.order.OrderComplaint;
import com.mimi.express.entity.order.param.OrderComplaintParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderComplaintMapper extends OrderMapper<OrderComplaint, OrderComplaintParam>{

    @Select({
            "SELECT * FROM t_order_complaint",
            BASE_CONDITION,DONE_CONDITION,EXPRESS_DELIVERY_CONDITION
    })
    @Override
    public List<OrderComplaint> findPage(OrderComplaintParam param);

    @Select({"SELECT count(0) FROM t_order_complaint",
            BASE_CONDITION,DONE_CONDITION,EXPRESS_DELIVERY_CONDITION})
    @Override
    public long findCount(OrderComplaintParam param);
}
