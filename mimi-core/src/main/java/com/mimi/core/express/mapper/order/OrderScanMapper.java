package com.mimi.core.express.mapper.order;

import com.mimi.core.express.entity.order.param.OrderParam;
import com.mimi.core.express.entity.order.OrderScan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderScanMapper extends OrderMapper<OrderScan>{

    @Select({"<script>",
            "SELECT t.* FROM t_order_scan t",
            BASE_CONDITION,EXPRESS_DELIVERY_CONDITION,TIME_CONDITION,ORDER,
            "</script>"
    })
    @Override
    public List<OrderScan> findPage(OrderParam<OrderScan> param);

    @Select({"<script>",
            "SELECT count(0) FROM t_order_scan t",
            BASE_CONDITION,EXPRESS_DELIVERY_CONDITION,TIME_CONDITION,
            "</script>"
    })
    @Override
    public long findCount(OrderParam<OrderScan> param);
}
