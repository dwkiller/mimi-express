package com.mimi.message.ext;

import com.mimi.express.entity.config.MsgVariable;
import com.mimi.express.entity.order.OrderAgent;
import com.mimi.message.ISendMsgExt;
import org.springframework.stereotype.Service;

/**
 * 快递代取商品派送通知
 */
@Service
public class AgentGet extends BaseMessageExt<OrderAgent> {
    @Override
    public void execute(OrderAgent order) {

    }

    @Override
    public String parameterize(OrderAgent order, MsgVariable msgVariable) {
        return null;
    }
}
