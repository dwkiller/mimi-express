package com.mimi.express.controller;


import com.mimi.core.common.superpackage.controller.SuperController;
import com.mimi.core.express.entity.config.Area;
import com.mimi.core.express.service.AreaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 宿舍区管理
 * </p>
 *
 * @author 宿舍区管理
 * @since 2023/10/31
 */
@Slf4j
@Tag(name = "宿舍区管理")
@RestController
@RequestMapping("/area")
public class AreaController extends SuperController<AreaService, Area> {

}
