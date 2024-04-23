package com.mimi.core.message.ext;

import com.mimi.core.express.entity.config.MsgVariable;
import com.mimi.core.express.entity.order.OrderIn;
import com.mimi.core.express.service.impl.order.OrderInService;
import com.mimi.core.message.ISendMsgExt;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class ExpressRetention implements ISendMsgExt<OrderIn> {

    @Autowired
    private OrderInService orderInService;

    @Override
    public void execute(OrderIn order, Map<String, String> sendParam, List<MsgVariable> msgVariableList) {
        if(!StringUtils.isEmpty(order.getMobile())){
            order.setSendMsg((short)1);
        }

        if(msgVariableList!=null){
            Optional<String> optional = msgVariableList.stream().filter(v->"更新现取货号".equals(v.getTag())).map(MsgVariable::getVariable).findFirst();
            if(optional.isPresent()){
                orderInService.stocktaking(order,sendParam.get(optional.get()));}
        }
    }
}
