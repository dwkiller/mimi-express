package com.mimi.express.entity.order.param;

import com.mimi.express.entity.order.OrderQuestion;
import lombok.Data;

@Data
public class OrderQuestionParam extends OrderQuestion implements BaseOrderParam {
    private int pageNum;
    private int pageSize;
}
