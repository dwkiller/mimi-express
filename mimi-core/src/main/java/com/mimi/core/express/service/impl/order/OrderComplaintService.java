package com.mimi.core.express.service.impl.order;

import com.mimi.core.express.entity.order.OrderComplaint;
import com.mimi.core.express.mapper.order.OrderComplaintMapper;
import org.springframework.stereotype.Service;

@Service
public class OrderComplaintService extends BaseOrderService<OrderComplaintMapper, OrderComplaint>{
    @Override
    public String type() {
        return "客户投诉";
    }
}
