package com.mimi.express.mapper.order;

import com.mimi.express.entity.order.OrderScan;
import com.mimi.express.entity.order.param.OrderParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderScanMapper extends OrderMapper<OrderScan>{

    @Select({"<script>",
            "SELECT * FROM t_order_scan",
            BASE_CONDITION,
            "</script>"
    })
    @Override
    public List<OrderScan> findPage(OrderParam<OrderScan> param);

    @Select({"<script>",
            "SELECT count(0) FROM t_order_scan",
            BASE_CONDITION,
            "</script>"
    })
    @Override
    public long findCount(OrderParam<OrderScan> param);
}
