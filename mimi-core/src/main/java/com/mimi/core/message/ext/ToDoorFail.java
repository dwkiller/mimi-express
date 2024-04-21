package com.mimi.core.message.ext;

import com.mimi.core.express.entity.config.MsgVariable;
import com.mimi.core.express.entity.order.OrderOnline;
import com.mimi.core.message.ISendMsgExt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Component
public class ToDoorFail  implements ISendMsgExt<OrderOnline> {
    @Override
    public void execute(OrderOnline order, Map<String, String> sendParam, List<MsgVariable> msgVariableList) {
        if(msgVariableList!=null){
            Optional<String> optional = msgVariableList.stream().filter(v->"失败原因".equals(v.getTag())).map(MsgVariable::getVariable).findFirst();
            if(optional.isPresent()){
                order.setFailReason(sendParam.get(optional.get()));
            }
        }
        order.setDone((short)2);
        order.setFailMsg((short)1);
    }
}
