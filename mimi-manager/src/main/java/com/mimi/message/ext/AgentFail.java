package com.mimi.message.ext;

import com.mimi.core.express.entity.config.MsgVariable;
import com.mimi.core.express.entity.config.PayAccount;
import com.mimi.core.express.entity.order.OrderAgent;
import com.mimi.core.express.service.PayAccountService;
import com.mimi.core.message.ISendMsgExt;
import com.mimi.core.wx.pay.WXPay;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class AgentFail implements ISendMsgExt<OrderAgent> {

    @Autowired
    private WXPay wxPay;
    @Autowired
    private PayAccountService payAccountService;

    @Override
    public void execute(OrderAgent order, Map<String, String> sendParam, List<MsgVariable> msgVariableList) {
        //退款
        String schoolId = order.getSchoolId();

        PayAccount payAccount = payAccountService.findBySchoolId(schoolId);
        if(payAccount==null){
            log.warn("学校["+schoolId+"]未配置商户号！");
            return;
        }
        order.setState((short)-1);
        order.setOutRefundNo(order.getPayOrder()+"_r");

        Map<String,Object> param = new HashMap<>();
        param.put("orderNum",order.getPayOrder());
        param.put("reOrderNum", order.getOutRefundNo());
        BigDecimal money = order.getMoney().setScale(2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));//转换成分
        param.put("money", money.intValue());
        param.put("refundDesc", "代取失败退款");

        try {
            wxPay.refund(payAccount,param);
        } catch (Exception e) {
            log.error("代取退款失败："+e.getMessage());
            e.printStackTrace();
            order.setState((short)-2);
        }
    }
}
