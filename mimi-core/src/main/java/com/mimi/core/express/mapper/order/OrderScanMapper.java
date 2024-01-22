package com.mimi.core.express.mapper.order;

import com.mimi.core.express.entity.order.param.OrderParam;
import com.mimi.core.express.entity.order.OrderScan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderScanMapper extends OrderMapper<OrderScan>{

    public static final String TIME_CONDITION="<if test='startTime != null'>"+
            " AND date_format(t_order_scan.create_time,'%Y-%m-%d %H:%i:%s') &gt; #{startTime}"+
            "</if>"+
            "<if test='endTime != null'>"+
            " AND date_format(t_order_scan.create_time,'%Y-%m-%d %H:%i:%s') &lt; #{endTime}"+
            "</if>";

    @Select({"<script>",
            "SELECT t_order_scan.* FROM t_order_scan",
            BASE_CONDITION,EXPRESS_DELIVERY_CONDITION,TIME_CONDITION,
            "</script>"
    })
    @Override
    public List<OrderScan> findPage(OrderParam<OrderScan> param);

    @Select({"<script>",
            "SELECT count(0) FROM t_order_scan",
            BASE_CONDITION,EXPRESS_DELIVERY_CONDITION,TIME_CONDITION,
            "</script>"
    })
    @Override
    public long findCount(OrderParam<OrderScan> param);
}
