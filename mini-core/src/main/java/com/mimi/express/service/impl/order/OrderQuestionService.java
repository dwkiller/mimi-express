package com.mimi.express.service.impl.order;

import com.mimi.express.entity.order.OrderQuestion;
import com.mimi.express.entity.order.param.OrderQuestionParam;
import com.mimi.express.mapper.order.OrderQuestionMapper;
import org.springframework.stereotype.Service;

@Service
public class OrderQuestionService extends BaseOrderService<OrderQuestionMapper, OrderQuestion, OrderQuestionParam> {
}
