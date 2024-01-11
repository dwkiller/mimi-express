package com.mimi.message;

import com.mimi.express.entity.config.MsgVariable;
import com.mimi.express.entity.order.BaseOrder;

import java.util.Map;

public interface WxMessageParameterize {

    public String parameterize(MsgVariable msgVariable);

}
