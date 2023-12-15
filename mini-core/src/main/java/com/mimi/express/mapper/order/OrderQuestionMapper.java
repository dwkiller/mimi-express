package com.mimi.express.mapper.order;

import com.mimi.express.entity.order.OrderQuestion;
import com.mimi.express.entity.order.param.OrderQuestionParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderQuestionMapper extends OrderMapper<OrderQuestion, OrderQuestionParam> {
    @Select({
            "SELECT * FROM t_order_question",
            BASE_CONDITION
    })
    @Override
    public List<OrderQuestion> findPage(OrderQuestionParam param);

    @Select({"SELECT count(0) FROM t_order_question",
            BASE_CONDITION})
    @Override
    public long findCount(OrderQuestionParam param);
}
