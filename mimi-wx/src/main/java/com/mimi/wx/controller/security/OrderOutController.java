package com.mimi.wx.controller.security;

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
import java.util.List;

@Slf4j
@Tag(name = "出库")
@RestController
@RequestMapping("/security/orderOut")
public class OrderOutController extends ReadOnlySuperController<OrderOutService, OrderOut> {

    @Value("${kd.outorder.fileroot}")
    private String rootPath;

    @Operation(summary = "批量保存出库单")
    @PostMapping("/saveBatch")
    public R<Collection<OrderOut>> saveBatch(@RequestBody Collection<OrderOut> entityList) throws Exception {
        if(superService.saveBatch(entityList,rootPath)){
            return R.success(entityList);
        }
        return R.error("保存失败!");
    }

    @Operation(summary = "批量更新出库单")
    @PostMapping("/updateBatch")
    public R updateBatch(@RequestBody List<OrderOut> orderList){
        superService.updateBatchById(orderList);
        return R.success();
    }

}
