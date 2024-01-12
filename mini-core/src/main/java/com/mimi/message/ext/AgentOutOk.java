package com.mimi.message.ext;

import com.mimi.express.entity.config.MsgVariable;
import com.mimi.express.entity.order.OrderAgent;
import com.mimi.message.ISendMsgExt;
import org.springframework.stereotype.Service;

/**
 * 代取快递下架成功提醒
 */
@Service
public class AgentOutOk extends BaseMessageExt<OrderAgent> {
    @Override
    public void execute(OrderAgent order) {

    }

    @Override
    public String parameterize(OrderAgent order, MsgVariable msgVariable) {
        return null;
    }
}
