package com.mimi.wx.controller.security;

import com.mimi.core.common.R;
import com.mimi.core.common.superpackage.controller.ReadOnlySuperController;
import com.mimi.core.express.entity.order.OrderOnline;
import com.mimi.core.express.service.impl.order.OrderOnlineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@Tag(name = "投诉运单")
@RestController
@RequestMapping("/orderOnline")
public class OrderOnlineController extends ReadOnlySuperController<OrderOnlineService, OrderOnline> {

    @Operation(summary = "新增实体")
    @PostMapping("")
    public R<Boolean> save(@RequestBody @Valid OrderOnline t) {
        return R.success(superService.save(t));
    }
}
