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

    @Select({"<script>",
            "SELECT t.* FROM t_order_in t",
            BASE_CONDITION,CONDITION,TIME_CONDITION,ORDER,
            "</script>"
    })
    @Override
    public List<OrderIn> findPage(OrderParam<OrderIn> param);

    @Select({"<script>",
            "SELECT count(0) FROM t_order_in t",
            BASE_CONDITION,CONDITION,TIME_CONDITION,
            "</script>"
    })
    @Override
    public long findCount(OrderParam<OrderIn> param);
}
