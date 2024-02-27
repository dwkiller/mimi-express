package com.mimi.express.controller.shop;

import com.mimi.core.common.superpackage.controller.SuperController;
import com.mimi.core.express.entity.shop.ShopCoupon;
import com.mimi.core.express.service.shop.ShopCouponService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "优惠卷")
@RestController
@RequestMapping("/coupon")
public class ShopCouponController extends SuperController<ShopCouponService, ShopCoupon> {


}
