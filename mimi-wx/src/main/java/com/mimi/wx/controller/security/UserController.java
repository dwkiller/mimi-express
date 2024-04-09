package com.mimi.wx.controller.security;

import com.mimi.core.common.R;
import com.mimi.core.common.util.RedisCache;
import com.mimi.core.express.entity.config.PayAccount;
import com.mimi.core.express.entity.config.PublicAccount;
import com.mimi.core.express.entity.order.OrderAgent;
import com.mimi.core.express.entity.receive.Insurance;
import com.mimi.core.express.entity.receive.Pricing;
import com.mimi.core.express.entity.shop.ShopCouponInst;
import com.mimi.core.express.service.InsuranceService;
import com.mimi.core.express.service.PayAccountService;
import com.mimi.core.express.service.PricingService;
import com.mimi.core.express.service.PublicAccountService;
import com.mimi.core.express.service.impl.order.OrderAgentService;
import com.mimi.core.express.service.shop.ShopCouponInstService;
import com.mimi.core.express.type.PayState;
import com.mimi.interceptor.UserInterceptor;
import com.mimi.util.pay.WXPay;
import com.mimi.util.pay.WXPayUtil;
import com.mimi.vo.PayReturnVo;
import com.mimi.vo.TokenVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Tag(name = "用户管理(已鉴权)")
@RestController
@RequestMapping("/security/user")
public class UserController {

    @Autowired
    private OrderAgentService orderAgentService;
    @Autowired
    private ShopCouponInstService shopCouponInstService;
    @Autowired
    private PayAccountService payAccountService;
    @Autowired
    private PricingService pricingService;
    @Autowired
    private InsuranceService insuranceService;
    @Autowired
    private PublicAccountService publicAccountService;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private WXPay wxPay;

    @Value("${kd.wx.notify.url}")
    private String notifyUrl;

    @RequestMapping("/payAgentOrder")
    @ResponseBody
    public R<PayReturnVo> payAgentOrder(HttpServletRequest request,@RequestBody OrderAgent orderAgent) throws Exception {
        String token = request.getHeader(UserInterceptor.ACCESS_TOKEN);
        ShopCouponInst shopCouponInst = null;
        TokenVo tokenVo = redisCache.getCacheObject(token);
//        TokenVo tokenVo = new TokenVo();
//        tokenVo.setOpenId("oeI4a6lElS00HRWEsnq7WB6GvZ14");
//        tokenVo.setToken("123456");
        String openId = tokenVo.getOpenId();
        String couponInstId = orderAgent.getCouponInstId();

        if(StringUtils.isEmpty(orderAgent.getPricingId())){
            throw new RuntimeException("缺少参数定价ID！");
        }

        PayAccount payAccount = payAccountService.findMy();
        if(payAccount==null){
            throw new RuntimeException("该学校未配置商户号！");
        }
        PublicAccount publicAccount = publicAccountService.getBySchoolId(payAccount.getSchoolId());

        BigDecimal money = BigDecimal.ZERO;
        Pricing pricing = pricingService.getById(orderAgent.getPricingId());
        if(pricing==null){
            throw new RuntimeException("不存在定价配置！");
        }
        money = money.add(pricing.getPrice());

        if(!StringUtils.isEmpty(orderAgent.getInsuranceId())){
            Insurance insurance = insuranceService.getById(orderAgent.getInsuranceId());
            money.add(insurance.getPrice());
        }

        if(orderAgent.getIndemnify()!=null&&orderAgent.getIndemnify().shortValue()!=0){
            money.add(new BigDecimal(0.3));
        }

        orderAgent.setPayOrder(UUID.randomUUID().toString().replaceAll("-",""));
        orderAgent.setFullAddress(orderAgent.getBuildName()+orderAgent.getRoomName());
        orderAgent.setMoney(money);

        if(!StringUtils.isEmpty(couponInstId)){
            shopCouponInst = shopCouponInstService.getById(couponInstId);
            if(shopCouponInst==null){
                throw new RuntimeException("优惠卷["+couponInstId+"]不存在");
            }
            money = money.subtract(shopCouponInst.getMoneyOffer());
        }
        money = money.setScale(2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));//转换成分

        Map<String,Object> param = new HashMap<>();
        param.put("spbill_create_ip", getIpAddress(request));
        param.put("notify_url", notifyUrl);
        param.put("orderNum", orderAgent.getPayOrder());
        param.put("money", money.intValue());
        param.put("openid", openId);
        Map<String,Object> unifiedMap = wxPay.unifiedOrder(payAccount,param);

        if ("FAIL".equals(unifiedMap.get("return_code"))) {
            return R.error("调用支付统一下单通信失败：" + String.valueOf(unifiedMap.get("return_msg")));
        }
        if ("FAIL".equals(unifiedMap.get("result_code"))) {
            return R.error("调用支付统一下单异常：" + String.valueOf(unifiedMap.get("err_code_des")));
        }
        String prePayId = unifiedMap.get("prepay_id").toString();

        log.info("调用支付统一下单成功");
        Map<String, String> map2 = new HashMap<>();
        Date date = new Date();
        String timestamp = String.valueOf(date.getTime());
        map2.put("appId", publicAccount.getAppId());
        map2.put("timeStamp",timestamp);
        String nonceStr = WXPayUtil.generateNonceStr();
        map2.put("nonceStr", nonceStr);
        map2.put("package","prepay_id="+prePayId);
        map2.put("signType","MD5");

        orderAgent.setPrepayId(prePayId);
        if(shopCouponInst!=null){
            orderAgent.setCouponInstId(shopCouponInst.getShopCouponId());
        }
        orderAgent.setPayState(PayState.PRE_PAY.getCode());
        orderAgentService.updateById(orderAgent);
        //发送消息


        log.info("再次签名，内容："+map2);
        String sign = WXPayUtil.generateSignature(map2, publicAccount.getAppSecret());

        PayReturnVo result = new PayReturnVo();
        result.setTimestamp(timestamp);
        result.setNonceStr(nonceStr);
        result.setPkg(map2.get("package"));
        result.setSign(sign);
        result.setSignType("MD5");
        result.setPrePayId(prePayId);

        log.info("微信支付成功，返回预付单："+prePayId);
        return R.success(result);
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");

        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        int index = ip.indexOf(",");
        if(index != -1){
            return ip.substring(0,index);
        }else{
            return ip;
        }
    }
}
