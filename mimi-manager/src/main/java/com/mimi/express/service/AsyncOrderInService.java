package com.mimi.express.service;

import com.mimi.core.express.entity.order.OrderIn;
import com.mimi.core.express.entity.user.User;
import com.mimi.core.express.service.UserService;
import com.mimi.core.express.service.impl.order.OrderInService;
import com.mimi.express.controller.order.vo.GrabOrderInVo;
import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.search.expression.Or;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class AsyncOrderInService {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderInService orderInService;

    @Async("robotGrabTaskExecutor")
    public void run(String schoolId, List<GrabOrderInVo> orderInVos){
        long now = System.currentTimeMillis();
        List<OrderIn> orderInList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(GrabOrderInVo grabOrderInVo:orderInVos){
            User user = null;
            if(!StringUtils.isEmpty(grabOrderInVo.getTelephone())
            &&grabOrderInVo.getTelephone().contains("*")){
                user = userService.findByLikeHeadBottom(schoolId,grabOrderInVo.getTelephone());
            }
            if(user!=null){
                grabOrderInVo.setTelephone(user.getMobile());
            }
            OrderIn orderIn = new OrderIn();
            orderIn.setSchoolId(schoolId);
            orderIn.setExpressCompany(grabOrderInVo.getCompany());
            orderIn.setOrderNum(grabOrderInVo.getOrderNum());
            orderIn.setRackNo(grabOrderInVo.getEntityNum());
            try {
                orderIn.setCreateTime(sdf.parse(grabOrderInVo.getIntime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            orderIn.setMobile(grabOrderInVo.getTelephone());
            if(user!=null) {
                orderIn.setUserName(user.getUserName());
            }
            orderInList.add(orderIn);
        }
        orderInService.saveBatch(orderInList);
        orderInList.clear();
        long dual = System.currentTimeMillis()-now;
        log.info("爬虫入库数据条数:"+orderInVos.size()+" , 耗时:"+dual+"毫秒");
    }


}
