package com.mimi.express.mapper.order;

import com.mimi.express.entity.order.OrderOnline;
import com.mimi.express.entity.order.param.OrderParam;
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
            "</if>";

    @Select({"<script>",
            "SELECT * FROM t_order_online",
            BASE_CONDITION,CONDITION,
            "</script>"
    })
    @Override
    public List<OrderOnline> findPage(OrderParam<OrderOnline> param);

    @Select({"<script>",
            "SELECT count(0) FROM t_order_online",
            BASE_CONDITION,CONDITION,
            "</script>"
    })
    @Override
    public long findCount(OrderParam<OrderOnline> param);
}
