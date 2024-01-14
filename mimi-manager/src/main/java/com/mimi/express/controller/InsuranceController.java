package com.mimi.express.controller;


import com.mimi.core.common.superpackage.controller.SuperController;
import com.mimi.core.express.entity.receive.Insurance;
import com.mimi.core.express.service.InsuranceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 保险管理
 * </p>
 *
 * @author 茹凯
 * @since 2023/10/31
 */
@Slf4j
@Tag(name = "保险管理")
@RestController
@RequestMapping("/insurance")
public class InsuranceController extends SuperController<InsuranceService, Insurance> {

}
