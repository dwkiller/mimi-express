package com.mimi.core.message.ext;

import com.mimi.core.express.entity.order.OrderIn;
import com.mimi.core.express.service.impl.order.OrderInService;
import com.mimi.core.message.ISendMsgExt;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class ExpressRetention implements ISendMsgExt<OrderIn> {

    @Autowired
    private OrderInService orderInService;

    @Override
    public void execute(OrderIn order, Map<String, String> sendParam) {
        String newRack = sendParam.get("character_string3.DATA");
        order.setSendMsg((short)1);
        orderInService.stocktaking(order,newRack);
    }
}
