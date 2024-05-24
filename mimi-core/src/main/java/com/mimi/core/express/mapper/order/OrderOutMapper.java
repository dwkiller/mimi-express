package com.mimi.core.express.mapper.order;

import com.mimi.core.express.entity.order.param.OrderParam;
import com.mimi.core.express.entity.order.OrderOut;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderOutMapper extends OrderMapper<OrderOut>{

    static final String CONDITION="<if test='businessData.sendMsg != null'>"+
            " AND send_msg = #{businessData.sendMsg}"+
            "</if>"+
            "<if test='businessData.realOut != null'>"+
            " AND real_out = #{businessData.realOut}"+
            "</if>"+
            "<if test='today != null'>"+
            " AND DATE(t_order_out.create_time) = CURDATE()"+
            "</if>";

    public static final String TIME_CONDITION="<if test='startTime != null'>"+
            " AND date_format(t_order_out.create_time,'%Y-%m-%d %H:%i:%s') &gt; #{startTime}"+
            "</if>"+
            "<if test='endTime != null'>"+
            " AND date_format(t_order_out.create_time,'%Y-%m-%d %H:%i:%s') &lt; #{endTime}"+
            "</if>";

    @Select({"<script>",
            "SELECT t_order_out.* FROM t_order_out",
            BASE_CONDITION,CONDITION,TIME_CONDITION,ORDER,
            "</script>"
    })
    @Override
    public List<OrderOut> findPage(OrderParam<OrderOut> param);

    @Select({"<script>","SELECT count(0) FROM t_order_out",
            BASE_CONDITION,CONDITION,TIME_CONDITION,
            "</script>"
    })
    @Override
    public long findCount(OrderParam<OrderOut> param);


}
