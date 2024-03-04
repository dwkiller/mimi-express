package com.mimi.core.message;

import com.mimi.core.express.entity.config.MsgVariable;
import com.mimi.core.express.entity.order.BaseOrder;

import java.util.Map;

public interface ISendMsgExt<T extends BaseOrder> {

    public void execute(T order, Map<String,String> sendParam);

}
