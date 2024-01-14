package com.mimi.core.express.service.impl.order;

import com.mimi.core.express.entity.order.OrderQuestion;
import com.mimi.core.express.mapper.order.OrderQuestionMapper;
import org.springframework.stereotype.Service;

@Service
public class OrderQuestionService extends BaseOrderService<OrderQuestionMapper, OrderQuestion> {
    @Override
    public String type() {
        return "问提运单";
    }
}
