package com.mimi.wx.controller.guest;


import com.mimi.common.superpackage.controller.ReadOnlySuperController;
import com.mimi.common.superpackage.controller.SuperController;
import com.mimi.express.entity.config.Building;
import com.mimi.express.service.BuildingService;
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
@RequestMapping("/building")
public class BuildingController extends ReadOnlySuperController<BuildingService, Building> {

}
