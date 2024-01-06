package com.mimi.express.service.impl.order;

import com.mimi.express.entity.config.ExpressDelivery;
import com.mimi.express.entity.order.OrderAgent;
import com.mimi.express.mapper.order.OrderAgentMapper;
import com.mimi.express.service.ExpressDeliveryService;
import com.mimi.express.type.InnerVariable;
import com.mimi.wx.WxService;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OrderAgentService extends BaseOrderService<OrderAgentMapper,OrderAgent>{

    @Autowired
    private WxService wxService;

    @Override
    public String type() {
        return "代取运单";
    }

    public void sendMsg(String templateId,OrderAgent orderAgent,Map<String,String> param) throws WxErrorException {
        wxService.sendMsg(templateId,orderAgent,param,(msgVariable)->{
            String result = "";
            if(InnerVariable.EXPRESS_DELIVERY_ADDRESS.getValue().equals(msgVariable.getVariable())){
                result = getExpressAddress(orderAgent);
            }else if(InnerVariable.GOODS_NUMBER.getValue().equals(msgVariable.getVariable())){
                result = orderAgent.getRackNo();
            }
            return result;
        });
    }
}
