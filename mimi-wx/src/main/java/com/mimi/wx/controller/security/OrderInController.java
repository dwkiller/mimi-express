package com.mimi.wx.controller.security;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mimi.core.common.R;
import com.mimi.core.common.superpackage.controller.ReadOnlySuperController;
import com.mimi.core.common.superpackage.param.PageParam;
import com.mimi.core.express.entity.order.OrderAgent;
import com.mimi.core.express.entity.order.OrderIn;
import com.mimi.core.express.entity.order.OrderOut;
import com.mimi.core.express.entity.order.param.OrderParam;
import com.mimi.core.express.service.impl.order.OrderAgentService;
import com.mimi.core.express.service.impl.order.OrderInService;
import com.mimi.core.express.service.impl.order.OrderOutService;
import com.mimi.vo.OrderInStateVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Tag(name = "入库")
@RestController
@RequestMapping("/security/orderIn")
public class OrderInController extends ReadOnlySuperController<OrderInService, OrderIn> {

    @Autowired
    private OrderOutService orderOutService;

    @Autowired
    private OrderAgentService orderAgentService;

    @Operation(summary = "查询带状态的的出库单")
    @PostMapping("/findPage")
    public R<IPage<?>> findPage(@RequestBody OrderParam<OrderIn> orderParam) {
        IPage<OrderIn> page = superService.findPage(orderParam);
        if(page.getRecords()==null||page.getRecords().size()==0){
            return R.success(page);
        }
        List<String> orderNumList = page.getRecords().stream().map(OrderIn::getOrderNum).collect(Collectors.toList());
        IPage<OrderInStateVo> result = new Page<>();
        result.setPages(page.getPages());
        result.setTotal(page.getTotal());
        result.setSize(page.getSize());
        result.setCurrent(page.getCurrent());
        result.setRecords(new ArrayList<>());
        for(OrderIn orderIn:page.getRecords()){
            OrderInStateVo orderInStateVo = new OrderInStateVo();
            BeanUtils.copyProperties(orderIn,orderInStateVo);
            orderInStateVo.setState("在库");
            result.getRecords().add(orderInStateVo);
        }

        List<OrderOut> existsOutList = orderOutService.existsOrderNum(orderNumList);
        List<OrderAgent> orderAgentList = orderAgentService.findByOrderNumList(orderNumList);

        result.getRecords().forEach(orderInStateVo -> {
            if(existsOutList!=null){
                Optional<OrderOut> optional = existsOutList.stream().filter(eo->eo.getOrderNum().equals(
                        orderInStateVo.getOrderNum())).findFirst();
                if(optional.isPresent()){
                    OrderOut orderOut = optional.get();
                    orderInStateVo.setState("已出库");
                    orderInStateVo.setOutUser(orderOut.getUserName());
                    orderInStateVo.setOutMobile(orderOut.getMobile());
                }
            }
            if(orderAgentList!=null){
                Optional<OrderAgent> optional = orderAgentList.stream().filter(eo->eo.getOrderNum().equals(
                        orderInStateVo.getOrderNum())).findFirst();
                if(optional.isPresent()){
                    OrderAgent orderAgent = optional.get();
                    if(orderAgent.getState()==1){
                        orderInStateVo.setState("取件派送中");
                    }else if(orderAgent.getState()==2){
                        orderInStateVo.setState("取件派送完成");
                    }
                    orderInStateVo.setAgentUser(orderAgent.getAgentName());
                    orderInStateVo.setAgentMobile(orderAgent.getAgentMobile());
                }
            }
        });
        return R.success(result);
    }

}
