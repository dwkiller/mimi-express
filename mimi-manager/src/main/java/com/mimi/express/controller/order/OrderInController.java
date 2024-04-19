package com.mimi.express.controller.order;

import com.mimi.core.common.R;
import com.mimi.core.express.entity.order.OrderIn;
import com.mimi.core.express.service.impl.order.OrderInService;
import com.mimi.express.controller.order.vo.StocktakingVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "投诉运单")
@RestController
@RequestMapping("/orderIn")
public class OrderInController extends BaseOrderController<OrderInService, OrderIn> {

    @Operation(summary = "移库")
    @PostMapping("/stocktaking")
    public R<?> stocktaking(@RequestBody StocktakingVo stocktakingVo){
        superService.stocktakingSave(stocktakingVo.getOrder(),stocktakingVo.getNewRack());
        return R.success();
    }

}
