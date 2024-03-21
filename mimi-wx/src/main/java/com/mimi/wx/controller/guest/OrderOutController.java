package com.mimi.wx.controller.guest;

import cn.hutool.core.io.FileUtil;
import com.mimi.core.common.R;
import com.mimi.core.common.superpackage.controller.ReadOnlySuperController;
import com.mimi.core.express.entity.order.OrderOut;
import com.mimi.core.express.service.impl.order.OrderOutService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Slf4j
@Tag(name = "出库")
@RestController
@RequestMapping("/guest/orderOut")
public class OrderOutController extends ReadOnlySuperController<OrderOutService, OrderOut> {

    @Value("${kd.outorder.fileroot}")
    private String rootPath;

    @Operation(summary = "批量保存出库单")
    @PostMapping("/saveBatch")
    public R saveBatch(@RequestBody Collection<OrderOut> entityList) throws Exception {
        if(superService.saveBatch(entityList,rootPath)){
            return R.success();
        }
        return R.error("保存失败!");
    }

}
