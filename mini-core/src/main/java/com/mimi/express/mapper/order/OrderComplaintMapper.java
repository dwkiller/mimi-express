package com.mimi.express.mapper.order;

import com.mimi.express.entity.order.OrderComplaint;
import com.mimi.express.entity.order.param.OrderParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderComplaintMapper extends OrderMapper<OrderComplaint>{

    @Select({"<script>",
            "SELECT * FROM t_order_complaint",
            BASE_CONDITION,
            "</script>"
    })
    @Override
    public List<OrderComplaint> findPage(OrderParam<OrderComplaint> param);

    @Select({"<script>",
            "SELECT count(0) FROM t_order_complaint",
            BASE_CONDITION,
            "</script>"
    })
    @Override
    public long findCount(OrderParam<OrderComplaint> param);
}
