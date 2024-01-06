package com.mimi.wx;

import com.mimi.express.entity.config.MsgVariable;
import com.mimi.express.entity.order.BaseOrder;

import java.util.Map;

public interface WxMessageParameterize {

    public <O extends BaseOrder>Object parameterize(MsgVariable msgVariable,O order);

}
