package com.mimi.express.service.impl.order;

import com.mimi.express.entity.order.OrderComplaint;
import com.mimi.express.mapper.order.OrderComplaintMapper;
import org.springframework.stereotype.Service;

@Service
public class OrderComplaintService extends BaseOrderService<OrderComplaintMapper, OrderComplaint>{
    @Override
    public String type() {
        return "客户投诉";
    }
}
