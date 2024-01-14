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

    @Select({"<script>",
            "SELECT * FROM t_order_question",
            BASE_CONDITION,CONDITION,EXPRESS_DELIVERY_CONDITION,
            "</script>"
    })
    @Override
    public List<OrderQuestion> findPage(OrderParam<OrderQuestion> param);

    @Select({"<script>",
            "SELECT count(0) FROM t_order_question",
            BASE_CONDITION,CONDITION,EXPRESS_DELIVERY_CONDITION,
            "</script>"})
    @Override
    public long findCount(OrderParam<OrderQuestion> param);
}
