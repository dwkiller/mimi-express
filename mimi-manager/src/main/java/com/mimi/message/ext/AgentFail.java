package com.mimi.message.ext;

import com.mimi.core.express.entity.order.OrderAgent;
import com.mimi.core.message.ISendMsgExt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class AgentFail implements ISendMsgExt<OrderAgent> {
    @Override
    public void execute(OrderAgent order, Map<String, String> sendParam) {
        //退款

    }
}
