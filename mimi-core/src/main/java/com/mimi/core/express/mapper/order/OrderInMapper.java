package com.mimi.core.express.mapper.order;

import com.mimi.core.express.entity.order.param.OrderParam;
import com.mimi.core.express.entity.order.OrderIn;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderInMapper extends OrderMapper<OrderIn>{

    static final String CONDITION="<if test='businessData.rackNo != null'>"+
            " AND rack_no = #{businessData.rackNo}"+
            "</if>"+
            "<if test='businessData.moveDb != null'>"+
            " AND move_db = #{businessData.moveDb}"+
            "</if>"+
            "<if test='businessData.sendMsg != null'>"+
            " AND send_msg = #{businessData.sendMsg}"+
            "</if>";

    public static final String TIME_CONDITION="<if test='startTime != null'>"+
            " AND date_format(t_order_in.create_time,'%Y-%m-%d %H:%i:%s') &gt; #{startTime}"+
            "</if>"+
            "<if test='endTime != null'>"+
            " AND date_format(t_order_in.create_time,'%Y-%m-%d %H:%i:%s') &lt; #{endTime}"+
            "</if>";


    @Select({"<script>",
            "SELECT t_order_in.* FROM t_order_in",
            BASE_CONDITION,CONDITION,TIME_CONDITION,ORDER,
            "</script>"
    })
    @Override
    public List<OrderIn> findPage(OrderParam<OrderIn> param);

    @Select({"<script>",
            "SELECT count(0) FROM t_order_in",
            BASE_CONDITION,CONDITION,TIME_CONDITION,
            "</script>"
    })
    @Override
    public long findCount(OrderParam<OrderIn> param);
}
