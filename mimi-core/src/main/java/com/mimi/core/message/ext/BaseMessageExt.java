package com.mimi.core.message.ext;

import com.mimi.core.express.entity.config.ExpressDelivery;
import com.mimi.core.express.entity.order.BaseOrder;
import com.mimi.core.express.entity.order.HasExpressDelivery;
import com.mimi.core.express.service.ExpressDeliveryService;
import com.mimi.core.message.ISendMsgExt;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseMessageExt<T extends BaseOrder> implements ISendMsgExt<T> {

    @Autowired
    private ExpressDeliveryService expressDeliveryService;

    protected String getExpressAddress(HasExpressDelivery hasExpressDelivery){
        ExpressDelivery expressDelivery = expressDeliveryService.getById(
                hasExpressDelivery.getExpressDeliveryId());
        if(expressDelivery==null){
            return "";
        }
        return expressDelivery.getName();
    }

}
