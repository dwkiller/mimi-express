package com.mimi.message.ext;

import com.mimi.express.entity.order.OrderAgent;
import com.mimi.message.ISendMsgExt;
import org.springframework.stereotype.Service;

/**
 * 快递代取商品派送通知
 */
@Service
public class AgentGet implements ISendMsgExt<OrderAgent> {
    @Override
    public void execute(OrderAgent order) {

    }
}
