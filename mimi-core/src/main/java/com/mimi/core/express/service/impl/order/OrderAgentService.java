package com.mimi.core.express.service.impl.order;

import com.mimi.core.express.entity.order.OrderAgent;
import com.mimi.core.express.mapper.order.OrderAgentMapper;
import com.mimi.core.message.MessageService;
import com.mimi.core.wx.WxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderAgentService extends BaseOrderService<OrderAgentMapper,OrderAgent>{

    @Autowired
    private WxService wxService;

    private MessageService<OrderAgent> messageService;

    @Override
    public String type() {
        return "代取运单";
    }

}
