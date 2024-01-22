package com.mimi.core.express.mapper.order;

import com.mimi.core.express.entity.order.param.OrderParam;
import com.mimi.core.express.entity.order.OrderComplaint;
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
            "</if>"+
            "<if test='businessData.done != null'>"+
            " AND done = #{businessData.done}"+
            "</if>";

    public static final String TIME_CONDITION="<if test='startTime != null'>"+
            " AND date_format(t_order_complaint.create_time,'%Y-%m-%d %H:%i:%s') &gt; #{startTime}"+
            "</if>"+
            "<if test='endTime != null'>"+
            " AND date_format(t_order_complaint.create_time,'%Y-%m-%d %H:%i:%s') &lt; #{endTime}"+
            "</if>";

    @Select({"<script>",
            "SELECT t_order_complaint.* FROM t_order_complaint",
            BASE_CONDITION,CONDITION,EXPRESS_DELIVERY_CONDITION,TIME_CONDITION,
            "</script>"
    })
    @Override
    public List<OrderComplaint> findPage(OrderParam<OrderComplaint> param);

    @Select({"<script>",
            "SELECT count(0) FROM t_order_complaint",
            BASE_CONDITION,CONDITION,EXPRESS_DELIVERY_CONDITION,TIME_CONDITION,
            "</script>"
    })
    @Override
    public long findCount(OrderParam<OrderComplaint> param);
}
