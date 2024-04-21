package com.mimi.core.message.ext;

import com.mimi.core.express.entity.config.MsgVariable;
import com.mimi.core.express.entity.order.OrderAgent;
import com.mimi.core.message.ISendMsgExt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class AgentHandleOk  implements ISendMsgExt<OrderAgent> {
    @Override
    public void execute(OrderAgent order, Map<String, String> sendParam, List<MsgVariable> msgVariableList) {
        order.setState((short)1);
    }
}
