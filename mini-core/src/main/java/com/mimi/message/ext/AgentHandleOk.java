package com.mimi.message.ext;

import com.mimi.express.entity.order.OrderAgent;
import com.mimi.message.ISendMsgExt;
import org.springframework.stereotype.Service;

/**
 * 代取快递受理成功提醒
 */
@Service
public class AgentHandleOk implements ISendMsgExt<OrderAgent> {
    @Override
    public void execute(OrderAgent order) {

    }
}
