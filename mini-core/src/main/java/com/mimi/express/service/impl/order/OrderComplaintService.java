package com.mimi.express.service.impl.order;

import com.mimi.express.entity.order.OrderComplaint;
import com.mimi.express.entity.order.param.OrderComplaintParam;
import com.mimi.express.mapper.order.OrderComplaintMapper;
import org.springframework.stereotype.Service;

@Service
public class OrderComplaintService extends BaseOrderService<OrderComplaintMapper, OrderComplaint, OrderComplaintParam>{
}
