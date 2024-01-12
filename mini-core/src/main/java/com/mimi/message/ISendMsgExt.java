package com.mimi.message;

import com.mimi.express.entity.config.MsgVariable;
import com.mimi.express.entity.order.BaseOrder;

public interface ISendMsgExt<T extends BaseOrder> {

    public void execute(T order);


    public String parameterize(T order,MsgVariable msgVariable);
}
