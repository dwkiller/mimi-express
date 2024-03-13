package com.mimi.core.express.service.impl.order;

import com.mimi.core.express.entity.order.OrderAgent;
import com.mimi.core.express.mapper.order.OrderAgentMapper;
import com.mimi.core.message.MessageService;
import com.mimi.core.wx.WxAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderAgentService extends BaseOrderService<OrderAgentMapper,OrderAgent>{


    @Override
    public String type() {
        return "代取运单";
    }

}
