package com.mimi.express.controller;


import com.mimi.common.superpackage.controller.SuperController;
import com.mimi.express.entity.receive.Pricing;
import com.mimi.express.service.PricingService;
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
@RequestMapping("/pricing")
public class PricingController extends SuperController<PricingService, Pricing> {

}
