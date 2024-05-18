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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<String> orderNumList = orderInVos.stream().map(GrabOrderInVo::getOrderNum).collect(Collectors.toList());
        List<String> existsOrderNumList = orderInService.existsOrderNum(schoolId,orderNumList);
        for(GrabOrderInVo grabOrderInVo:orderInVos){
            if(existsOrderNumList!=null&&existsOrderNumList.contains(grabOrderInVo.getOrderNum())){
                continue;
            }
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
            orderIn.setExpressCompany(unicodeToCN(grabOrderInVo.getCompany()));
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
        if(orderInList.size()>0){
            orderInService.saveBatch(orderInList);
        }
        orderInList.clear();
        long dual = System.currentTimeMillis()-now;
        log.info("爬虫入库数据条数:"+orderInVos.size()+" , 耗时:"+dual+"毫秒");
    }

    public static String unicodeToCN(String str) {
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            str = str.replace(matcher.group(1), ch + "");
        }
        return str;
    }
    public static void main(String[] args){
        String s = unicodeToCN("\u7533\u901a\u5feb\u9012");
        String s1 = unicodeToCN("我是");
        System.out.println(s);
        System.out.println(s1);
    }
}
