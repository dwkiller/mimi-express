package com.mimi.express.mapper.order;

import com.mimi.express.entity.order.OrderQuestion;
import com.mimi.express.entity.order.param.OrderParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderQuestionMapper extends OrderMapper<OrderQuestion> {

    static final String CONDITION="<if test='businessData.descContent != null'>"+
            " AND desc_content = #{businessData.descContent}"+
            "</if>";

    @Select({"<script>",
            "SELECT * FROM t_order_question",
            BASE_CONDITION,CONDITION,
            "</script>"
    })
    @Override
    public List<OrderQuestion> findPage(OrderParam<OrderQuestion> param);

    @Select({"<script>",
            "SELECT count(0) FROM t_order_question",
            BASE_CONDITION,CONDITION,
            "</script>"})
    @Override
    public long findCount(OrderParam<OrderQuestion> param);
}
