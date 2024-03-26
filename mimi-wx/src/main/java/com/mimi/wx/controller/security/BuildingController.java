package com.mimi.wx.controller.security;


import com.mimi.core.common.superpackage.controller.ReadOnlySuperController;
import com.mimi.core.express.entity.config.Building;
import com.mimi.core.express.service.BuildingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 楼栋管理
 * </p>
 *
 * @author 楼栋管理
 * @since 2023/10/31
 */
@Slf4j
@Tag(name = "楼栋管理")
@RestController
@RequestMapping("/security/building")
public class BuildingController extends ReadOnlySuperController<BuildingService, Building> {

}
