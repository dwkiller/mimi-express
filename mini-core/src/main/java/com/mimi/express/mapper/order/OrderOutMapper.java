package com.mimi.express.mapper.order;

import com.mimi.express.entity.order.OrderOut;
import com.mimi.express.entity.order.param.OrderParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderOutMapper extends OrderMapper<OrderOut>{

    static final String CONDITION="<if test='businessData.sendMsg != null'>"+
            " AND send_msg = #{businessData.sendMsg}"+
            "</if>";


    @Select({"<script>",
            "SELECT * FROM t_order_out",
            BASE_CONDITION,CONDITION,
            "</script>"
    })
    @Override
    public List<OrderOut> findPage(OrderParam<OrderOut> param);

    @Select({"<script>","SELECT count(0) FROM t_order_out",
            BASE_CONDITION,CONDITION,
            "</script>"
    })
    @Override
    public long findCount(OrderParam<OrderOut> param);


}
