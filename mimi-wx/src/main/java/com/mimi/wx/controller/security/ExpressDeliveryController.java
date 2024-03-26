package com.mimi.wx.controller.security;


import com.mimi.core.common.superpackage.controller.ReadOnlySuperController;
import com.mimi.core.express.entity.config.ExpressDelivery;
import com.mimi.core.express.service.ExpressDeliveryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 快递公司管理
 * </p>
 *
 * @author 茹凯
 * @since 2023/10/31
 */
@Slf4j
@Tag(name = "快递公司管理")
@RestController
@RequestMapping("/security/expressDelivery")
public class ExpressDeliveryController extends ReadOnlySuperController<ExpressDeliveryService, ExpressDelivery> {

}
