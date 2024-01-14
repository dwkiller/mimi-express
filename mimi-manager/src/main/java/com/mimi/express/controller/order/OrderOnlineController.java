package com.mimi.express.controller.order;

import com.mimi.core.express.entity.order.OrderOnline;
import com.mimi.core.express.service.impl.order.OrderOnlineService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "投诉运单")
@RestController
@RequestMapping("/orderOnline")
public class OrderOnlineController extends BaseOrderController<OrderOnlineService, OrderOnline> {
}
