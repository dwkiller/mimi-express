package com.mimi.message.ext;

import com.mimi.express.entity.order.OrderAgent;
import com.mimi.message.ISendMsgExt;
import org.springframework.stereotype.Service;

/**
 * 快递代取商品延期派送通知
 */
@Service
public class AgentGetDelay implements ISendMsgExt<OrderAgent> {
    @Override
    public void execute(OrderAgent order) {

    }
}
