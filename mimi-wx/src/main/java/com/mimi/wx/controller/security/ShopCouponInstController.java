package com.mimi.wx.controller.security;

import com.mimi.core.common.superpackage.controller.ReadOnlySuperController;
import com.mimi.core.express.entity.shop.ShopCouponInst;
import com.mimi.core.express.service.shop.ShopCouponInstService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 价格管理
 * </p>
 *
 * @author
 * @since 2023/10/31
 */
@Slf4j
@Tag(name = "优惠卷管理")
@RestController
@RequestMapping("/security/shopCoupon")
public class ShopCouponInstController extends ReadOnlySuperController<ShopCouponInstService, ShopCouponInst> {

}
