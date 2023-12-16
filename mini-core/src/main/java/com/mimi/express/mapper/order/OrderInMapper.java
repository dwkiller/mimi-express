package com.mimi.express.mapper.order;

import com.mimi.express.entity.order.OrderIn;
import com.mimi.express.entity.order.param.OrderParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderInMapper extends OrderMapper<OrderIn>{
    @Select({"<script>",
            "SELECT * FROM t_order_in",
            BASE_CONDITION,
            "</script>"
    })
    @Override
    public List<OrderIn> findPage(OrderParam<OrderIn> param);

    @Select({"<script>",
            "SELECT count(0) FROM t_order_in",
            BASE_CONDITION,
            "</script>"
    })
    @Override
    public long findCount(OrderParam<OrderIn> param);
}
