package com.mimi.express.controller.shop;

import com.mimi.core.common.superpackage.controller.SuperController;
import com.mimi.core.express.entity.shop.ShopCouponInst;
import com.mimi.core.express.service.shop.ShopCouponInstService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "优惠卷使用")
@RestController
@RequestMapping("/couponInst")
public class ShopCouponInstController extends SuperController<ShopCouponInstService, ShopCouponInst>{
}
