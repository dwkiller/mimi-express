package com.mimi.wx.controller.security;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mimi.core.common.R;
import com.mimi.core.common.superpackage.controller.ReadOnlySuperController;
import com.mimi.core.common.superpackage.param.PageParam;
import com.mimi.core.express.entity.order.OrderAgent;
import com.mimi.core.express.entity.order.OrderIn;
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

        List<String> existsOutList = orderOutService.existsOrderNum(orderNumList);
        if(existsOutList!=null&&existsOutList.size()>0){
            result.getRecords().stream().filter(
                    o->existsOutList.contains(o.getOrderNum())).forEach(o->o.setState("已出库"));
        }

        List<OrderAgent> orderAgentList = orderAgentService.findByOrderNumList(orderNumList);
        if(orderAgentList==null){
            orderAgentList=new ArrayList<>();
        }
        List<String> existsAgentIngList = orderAgentList.stream().filter(o->o.getState()==1)
                .map(OrderAgent::getOrderNum).collect(Collectors.toList());
        List<String> existsAgentDoneList = orderAgentList.stream().filter(o->o.getState()==2)
                .map(OrderAgent::getOrderNum).collect(Collectors.toList());
        if(existsAgentIngList!=null){
            result.getRecords().stream().filter(
                    o->existsAgentIngList.contains(o.getOrderNum())).forEach(o->o.setState("取件派送中"));
        }
        if(existsAgentDoneList!=null){
            result.getRecords().stream().filter(
                    o->existsAgentDoneList.contains(o.getOrderNum())).forEach(o->o.setState("取件派送完成"));
        }
        return R.success(result);
    }

}
