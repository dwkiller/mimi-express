package com.mimi.core.message.ext;


import com.mimi.core.express.entity.config.MsgVariable;
import com.mimi.core.express.entity.order.OrderAgent;
import com.mimi.core.express.type.InnerVariable;
import org.springframework.stereotype.Service;

/**
 * 代取快递失败提醒
 */
@Service
public class AgentFail extends BaseMessageExt<OrderAgent> {
    @Override
    public void execute(OrderAgent order) {

    }

    @Override
    public String parameterize(OrderAgent orderAgent, MsgVariable msgVariable) {
        String result = "";
        if(InnerVariable.EXPRESS_DELIVERY_ADDRESS.getValue().equals(msgVariable.getVariable())){
            result = getExpressAddress(orderAgent);
        }else if(InnerVariable.GOODS_NUMBER.getValue().equals(msgVariable.getVariable())){
            result = orderAgent.getRackNo();
        }
        return result;
    }
}
