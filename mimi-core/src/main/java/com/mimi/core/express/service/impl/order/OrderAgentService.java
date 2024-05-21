package com.mimi.core.express.service.impl.order;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mimi.core.express.entity.order.OrderAgent;
import com.mimi.core.express.mapper.order.OrderAgentMapper;
import com.mimi.core.message.MessageService;
import com.mimi.core.wx.WxAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderAgentService extends BaseOrderService<OrderAgentMapper,OrderAgent>{


    @Override
    public String type() {
        return "代取运单";
    }

    public List<OrderAgent> findByOrderNumList(List<String> orderNumList){
        LambdaQueryWrapper<OrderAgent> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(OrderAgent::getOrderNum,orderNumList);
        return super.list(wrapper);
    }

    public OrderAgent findByPayOrder(String payOrder){
        LambdaQueryWrapper<OrderAgent> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderAgent::getPayOrder,payOrder);
        return baseMapper.selectOne(wrapper);
    }

}
