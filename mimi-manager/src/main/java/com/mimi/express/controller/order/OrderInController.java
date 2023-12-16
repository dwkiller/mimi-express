package com.mimi.express.controller.order;

import com.mimi.express.entity.order.OrderIn;
import com.mimi.express.service.impl.order.OrderInService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "投诉运单")
@RestController
@RequestMapping("/orderIn")
public class OrderInController extends BaseOrderController<OrderInService, OrderIn> {
}
