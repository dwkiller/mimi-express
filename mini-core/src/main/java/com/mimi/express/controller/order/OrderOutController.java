package com.mimi.express.controller.order;

import com.mimi.express.entity.order.OrderOut;
import com.mimi.express.entity.order.param.OrderOutParam;
import com.mimi.express.service.impl.order.OrderOutService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "投诉运单")
@RestController
@RequestMapping("/orderOut")
public class OrderOutController extends BaseOrderController<OrderOutService, OrderOut, OrderOutParam>{
}
