package com.mimi.core.message.ext;

import com.mimi.core.express.entity.config.MsgVariable;
import com.mimi.core.express.entity.order.OrderAgent;
import org.springframework.stereotype.Service;

/**
 * 代取快递受理成功提醒
 */
@Service
public class AgentHandleOk extends BaseMessageExt<OrderAgent> {
    @Override
    public void execute(OrderAgent order) {

    }

    @Override
    public String parameterize(OrderAgent order, MsgVariable msgVariable) {
        return null;
    }
}
