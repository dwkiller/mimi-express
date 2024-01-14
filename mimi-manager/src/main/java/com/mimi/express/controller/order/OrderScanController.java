package com.mimi.express.controller.order;

import com.mimi.core.express.entity.order.OrderScan;
import com.mimi.core.express.service.impl.order.OrderScanService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "投诉运单")
@RestController
@RequestMapping("/orderScan")
public class OrderScanController extends BaseOrderController<OrderScanService, OrderScan> {

}
