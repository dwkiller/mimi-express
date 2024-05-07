package com.mimi.express.controller.order;

import cn.hutool.extra.spring.SpringUtil;
import com.mimi.core.common.R;
import com.mimi.core.express.entity.order.BaseOrder;
import com.mimi.core.express.service.IBaseOrderService;
import com.mimi.message.vo.CommOrderVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Slf4j
@Tag(name = "通用查询")
@RestController
@RequestMapping("/commQuery")
public class CommQuery {

    @Operation(summary = "根据订单号查询")
    @GetMapping("/order/{orderNum}")
    public R<List<CommOrderVo>> findByOrderNum(@PathVariable String orderNum) throws Exception {
        List<CommOrderVo> result = new ArrayList<>();
        Map<String,IBaseOrderService> beanMaps =  SpringUtil.getBeansOfType(IBaseOrderService.class);
        Iterator<IBaseOrderService> beans = beanMaps.values().iterator();
        while(beans.hasNext()){
            IBaseOrderService bean = beans.next();
            List<BaseOrder> orderList = bean.findMultipleByOrderNum(orderNum);
            if(orderList!=null&&orderList.size()>0){
                for(BaseOrder order:orderList){
                    CommOrderVo orderVo = new CommOrderVo();
                    orderVo.setOrderNum(order.getOrderNum());
                    orderVo.setType(bean.type());
                    orderVo.setCreateTime(order.getCreateTime());
                    result.add(orderVo);
                }
            }
        }
        return R.success(result);
    }

}
