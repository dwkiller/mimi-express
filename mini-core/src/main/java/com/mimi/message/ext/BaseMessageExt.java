package com.mimi.message.ext;

import com.mimi.express.entity.config.ExpressDelivery;
import com.mimi.express.entity.order.BaseOrder;
import com.mimi.express.entity.order.HasExpressDelivery;
import com.mimi.express.service.ExpressDeliveryService;
import com.mimi.message.ISendMsgExt;
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
