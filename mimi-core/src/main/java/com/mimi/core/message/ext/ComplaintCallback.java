package com.mimi.core.message.ext;

import com.mimi.core.express.entity.config.MsgVariable;
import com.mimi.core.express.entity.order.OrderComplaint;
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
