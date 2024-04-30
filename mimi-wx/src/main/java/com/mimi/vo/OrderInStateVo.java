package com.mimi.vo;

import com.mimi.core.express.entity.order.OrderIn;
import lombok.Data;

@Data
public class OrderInStateVo {

    private OrderIn orderIn;
    //代取中
    private boolean agenting;
    //代取完成
    private boolean agented;
    //已出库
    private boolean outed;

}
