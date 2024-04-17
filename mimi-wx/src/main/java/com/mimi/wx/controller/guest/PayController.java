package com.mimi.wx.controller.guest;


import com.mimi.core.express.entity.config.PayAccount;
import com.mimi.core.express.entity.order.OrderAgent;
import com.mimi.core.express.service.PayAccountService;
import com.mimi.core.express.service.PublicAccountService;
import com.mimi.core.express.service.impl.order.OrderAgentService;
import com.mimi.core.express.type.PayState;
import com.mimi.core.wx.pay.WXPayUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * <p>
 * 支付
 * </p>
 *
 * @author
 * @since 2023/10/31
 */
@Slf4j
@Tag(name = "支付")
@RestController
@RequestMapping("/guest/pay")
public class PayController {

    @Autowired
    private OrderAgentService orderAgentService;

    @Autowired
    private PayAccountService payAccountService;

    @Autowired
    private PublicAccountService publicAccountService;

    @RequestMapping("/notice")
    @ResponseBody
    public synchronized String notice(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("微信支付回调开始");
        String inputLine;
        String notityXml = "";
        while ((inputLine = request.getReader().readLine()) != null) {
            notityXml += inputLine;
        }
        request.getReader().close();
        log.info("接收到的报文：" + notityXml);
        if (StringUtils.isEmpty(notityXml)) {
            response.getWriter().print("接收参数为空");
            return null;
        }

        Map<String, String> data = WXPayUtil.xmlToMap(notityXml);
        if("SUCCESS".equals(data.get("result_code"))){
            String payOrder = data.get("out_trade_no");
            OrderAgent orderAgent = orderAgentService.findByPayOrder(payOrder);
            //PublicAccount publicAccount = publicAccountService.getBySchoolId(orderAgent.getSchoolId());
            PayAccount payAccount = payAccountService.findBySchoolId(orderAgent.getSchoolId());
            if(!WXPayUtil.isSignatureValid(data,payAccount.getAppKey())){
                log.error("验证签名失败");
                return WXPayUtil.setXML("FAIL", "验证签名失败");
            }
            orderAgent.setPayMoney(orderAgent.getMoney());
            orderAgent.setPayState(PayState.PAY.getCode());
            orderAgentService.updateById(orderAgent);
        }else{
            log.error("微信支付回调失败，失败原因" + String.valueOf(data.get("err_code_des")));
            return WXPayUtil.setXML("FAIL", data.get("err_code_des"));
        }
        log.info("微信支付回调结束");
        return "success";
    }

}
