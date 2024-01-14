package com.mimi.core.message;

import com.mimi.core.express.entity.config.MsgVariable;
import com.mimi.core.express.entity.order.BaseOrder;

public interface ISendMsgExt<T extends BaseOrder> {

    public void execute(T order);


    public String parameterize(T order,MsgVariable msgVariable);
}
