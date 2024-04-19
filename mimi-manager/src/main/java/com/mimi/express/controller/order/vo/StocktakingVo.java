package com.mimi.express.controller.order.vo;

import com.mimi.core.express.entity.order.OrderIn;
import lombok.Data;

@Data
public class StocktakingVo {
    private OrderIn order;
    private String newRack;
}
