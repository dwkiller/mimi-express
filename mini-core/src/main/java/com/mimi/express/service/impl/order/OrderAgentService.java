package com.mimi.express.service.impl.order;

import com.mimi.express.entity.order.OrderAgent;
import com.mimi.express.mapper.order.OrderAgentMapper;
import com.mimi.wx.WxService;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.stereotype.Service;

@Service
public class OrderAgentService extends BaseOrderService<OrderAgentMapper,OrderAgent>{

    private WxService wxService;

    @Override
    public String type() {
        return "代取运单";
    }

    public void sendMsg(String templateId,OrderAgent orderAgent) throws WxErrorException {
        wxService.sendMsg(templateId,orderAgent,(msgVariable)->{

            return "";
        });
    }
}
