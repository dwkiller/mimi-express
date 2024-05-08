package com.mimi.core.express.mapper.order;

import com.mimi.core.express.entity.order.param.OrderParam;
import com.mimi.core.express.entity.order.OrderQuestion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderQuestionMapper extends OrderMapper<OrderQuestion> {

    static final String CONDITION="<if test='businessData.descContent != null'>"+
            " AND desc_content = #{businessData.descContent}"+
            "</if>"+
            "<if test='businessData.done != null'>"+
            " AND done = #{businessData.done}"+
            "</if>";

    public static final String TIME_CONDITION="<if test='startTime != null'>"+
            " AND date_format(t_order_question.create_time,'%Y-%m-%d %H:%i:%s') &gt; #{startTime}"+
            "</if>"+
            "<if test='endTime != null'>"+
            " AND date_format(t_order_question.create_time,'%Y-%m-%d %H:%i:%s') &lt; #{endTime}"+
            "</if>";

    @Select({"<script>",
            "SELECT t_order_question.* FROM t_order_question",
            BASE_CONDITION,CONDITION,EXPRESS_DELIVERY_CONDITION,TIME_CONDITION,ORDER,
            "</script>"
    })
    @Override
    public List<OrderQuestion> findPage(OrderParam<OrderQuestion> param);

    @Select({"<script>",
            "SELECT count(0) FROM t_order_question",
            BASE_CONDITION,CONDITION,EXPRESS_DELIVERY_CONDITION,TIME_CONDITION,
            "</script>"})
    @Override
    public long findCount(OrderParam<OrderQuestion> param);
}
