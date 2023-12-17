package com.mimi.express.mapper.order;

import com.mimi.express.entity.order.OrderComplaint;
import com.mimi.express.entity.order.param.OrderParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderComplaintMapper extends OrderMapper<OrderComplaint>{

    static final String CONDITION="<if test='businessData.type != null'>"+
            " AND type = #{businessData.type}"+
            "</if>"+
            "<if test='businessData.handMsg != null'>"+
            " AND hand_msg = #{businessData.handMsg}"+
            "</if>"+
            "<if test='businessData.doneMsg != null'>"+
            " AND done_msg = #{businessData.doneMsg}"+
            "</if>";

    @Select({"<script>",
            "SELECT * FROM t_order_complaint",
            BASE_CONDITION,CONDITION,
            "</script>"
    })
    @Override
    public List<OrderComplaint> findPage(OrderParam<OrderComplaint> param);

    @Select({"<script>",
            "SELECT count(0) FROM t_order_complaint",
            BASE_CONDITION,CONDITION,
            "</script>"
    })
    @Override
    public long findCount(OrderParam<OrderComplaint> param);
}
