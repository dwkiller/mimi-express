package com.mimi.express.controller.shop;

import com.mimi.core.common.R;
import com.mimi.core.common.superpackage.controller.SuperController;
import com.mimi.core.express.entity.shop.ShopCouponInst;
import com.mimi.core.express.service.shop.ShopCouponInstService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Tag(name = "优惠卷使用")
@RestController
@RequestMapping("/couponInst")
public class ShopCouponInstController extends SuperController<ShopCouponInstService, ShopCouponInst>{

    @Operation(summary = "批量新增实体")
    @PostMapping("/batchSave")
    public R<Boolean> save(@RequestBody @Valid List<ShopCouponInst> list) {
        return R.success(superService.saveBatch(list));
    }
}
