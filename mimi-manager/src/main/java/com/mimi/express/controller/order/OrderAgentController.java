package com.mimi.express.controller.order;

import com.mimi.core.express.entity.order.OrderAgent;
import com.mimi.core.express.service.impl.order.OrderAgentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "代理运单")
@RestController
@RequestMapping("/orderAgent")
public class OrderAgentController extends BaseOrderController<OrderAgentService, OrderAgent>{
}
