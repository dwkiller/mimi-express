package com.mimi.core.express.service.impl.order;

import com.mimi.core.express.entity.order.OrderIn;
import com.mimi.core.express.mapper.order.OrderInMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class OrderInService extends BaseOrderService<OrderInMapper, OrderIn> {
    @Override
    public String type() {
        return "入库单";
    }


    public void stocktaking(OrderIn order,String newRack){
        log.info("更新货架号"+order.getRackNo()+"->"+newRack);
        if(!StringUtils.isEmpty(newRack)){
            order.setRackNo(newRack);
        }
        order.setMoveDbTime(new Date());
        Integer moveTimes = order.getMoveDbTimes();
        if(moveTimes==null){
            moveTimes = 0;
        }
        moveTimes++;
        order.setMoveDbTimes(moveTimes);
        order.setMoveDb((short)1);
    }

    public void stocktakingSave(OrderIn order,String newRack){
        stocktaking(order,newRack);
        this.updateById(order);
    }

}
