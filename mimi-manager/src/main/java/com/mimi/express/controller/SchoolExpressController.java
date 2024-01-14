package com.mimi.express.controller;


import com.mimi.core.common.superpackage.controller.SuperController;
import com.mimi.core.express.entity.relation.SchoolExpress;
import com.mimi.core.express.service.SchoolExpressService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 学校快递管理
 * </p>
 *
 * @author 茹凯
 * @since 2023/10/31
 */
@Slf4j
@Tag(name = "学校快递管理")
@RestController
@RequestMapping("/schoolExpress")
public class SchoolExpressController extends SuperController<SchoolExpressService, SchoolExpress> {



}
