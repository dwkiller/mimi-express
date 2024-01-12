package com.mimi.message.ext;

import com.mimi.express.entity.config.MsgVariable;
import com.mimi.express.entity.order.OrderIn;
import com.mimi.message.ISendMsgExt;
import org.springframework.stereotype.Service;

/**
 * 快递到站通知
 */
@Service
public class ExpressArrive extends BaseMessageExt<OrderIn> {
    @Override
    public void execute(OrderIn order) {

    }

    @Override
    public String parameterize(OrderIn order, MsgVariable msgVariable) {
        return null;
    }
}
