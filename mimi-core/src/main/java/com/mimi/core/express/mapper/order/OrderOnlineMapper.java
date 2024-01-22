package com.mimi.core.express.mapper.order;

import com.mimi.core.express.entity.order.param.OrderParam;
import com.mimi.core.express.entity.order.OrderOnline;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderOnlineMapper extends OrderMapper<OrderOnline>{

    static final String CONDITION="<if test='businessData.source != null'>"+
            " AND source = #{businessData.source}"+
            "</if>"+
            "<if test='businessData.failReason != null'>"+
            " AND fail_reason = #{businessData.failReason}"+
            "</if>"+
            "<if test='businessData.done != null'>"+
            " AND done = #{businessData.done}"+
            "</if>";

    public static final String TIME_CONDITION="<if test='startTime != null'>"+
            " AND date_format(t_order_online.create_time,'%Y-%m-%d %H:%i:%s') &gt; #{startTime}"+
            "</if>"+
            "<if test='endTime != null'>"+
            " AND date_format(t_order_online.create_time,'%Y-%m-%d %H:%i:%s') &lt; #{endTime}"+
            "</if>";

    @Select({"<script>",
            "SELECT t_order_online.* FROM t_order_online",
            BASE_CONDITION,CONDITION,TIME_CONDITION,
            "</script>"
    })
    @Override
    public List<OrderOnline> findPage(OrderParam<OrderOnline> param);

    @Select({"<script>",
            "SELECT count(0) FROM t_order_online",
            BASE_CONDITION,CONDITION,TIME_CONDITION,
            "</script>"
    })
    @Override
    public long findCount(OrderParam<OrderOnline> param);
}
