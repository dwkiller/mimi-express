package com.mimi.message.ext;

import com.mimi.express.entity.config.MsgVariable;
import com.mimi.express.entity.order.OrderComplaint;
import com.mimi.message.ISendMsgExt;
import org.springframework.stereotype.Service;

/**
 * 快递投诉回复通知
 */
@Service
public class ComplaintCallback extends BaseMessageExt<OrderComplaint> {
    @Override
    public void execute(OrderComplaint order) {

    }

    @Override
    public String parameterize(OrderComplaint order, MsgVariable msgVariable) {
        return null;
    }
}
