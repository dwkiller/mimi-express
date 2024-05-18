package com.mimi.express.controller.order;

import com.mimi.core.common.R;
import com.mimi.core.express.entity.config.School;
import com.mimi.core.express.entity.order.OrderIn;
import com.mimi.core.express.service.SchoolService;
import com.mimi.core.express.service.impl.order.OrderInService;
import com.mimi.express.controller.order.vo.GrabOrderInVo;
import com.mimi.express.controller.order.vo.RobotGrabVo;
import com.mimi.express.controller.order.vo.StocktakingVo;
import com.mimi.express.service.AsyncOrderInService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Tag(name = "投诉运单")
@RestController
@RequestMapping("/orderIn")
public class OrderInController extends BaseOrderController<OrderInService, OrderIn> {

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private AsyncOrderInService asyncOrderInService;

    @Operation(summary = "移库")
    @PostMapping("/stocktaking")
    public R<?> stocktaking(@RequestBody StocktakingVo stocktakingVo){
        superService.stocktakingSave(stocktakingVo.getOrder(),stocktakingVo.getNewRack());
        return R.success();
    }

    @Operation(summary = "爬虫入库")
    @PostMapping("/acceptOrderIn")
    public R<?> acceptOrderIn(@RequestBody RobotGrabVo robotGrabVo){
        School school = schoolService.findByName(robotGrabVo.getSchool());
        if(school==null){
            return R.error("学校["+robotGrabVo.getSchool()+"]不存在");
        }
        List<GrabOrderInVo> grabOrderInVoList = robotGrabVo.getData();
        if(grabOrderInVoList==null||grabOrderInVoList.size()==0){
            return R.success();
        }
        asyncOrderInService.run(school.getId(),grabOrderInVoList);
        return R.success();
    }

}
