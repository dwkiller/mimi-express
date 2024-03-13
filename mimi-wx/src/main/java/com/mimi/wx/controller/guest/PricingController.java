package com.mimi.wx.controller.guest;


import com.mimi.core.common.superpackage.controller.ReadOnlySuperController;
import com.mimi.core.express.entity.receive.Pricing;
import com.mimi.core.express.service.PricingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 价格管理
 * </p>
 *
 * @author 茹凯
 * @since 2023/10/31
 */
@Slf4j
@Tag(name = "价格管理")
@RestController
@RequestMapping("/guest/pricing")
public class PricingController extends ReadOnlySuperController<PricingService, Pricing> {

}
