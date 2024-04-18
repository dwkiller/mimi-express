package com.mimi.wx.controller.security;

import com.mimi.core.common.R;
import com.mimi.core.common.superpackage.redis.CacheManager;
import com.mimi.core.common.util.RedisCache;
import com.mimi.core.common.util.UserInfoUtil;
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
import com.mimi.core.wx.WxAppService;
import com.mimi.interceptor.UserInterceptor;
import com.mimi.core.wx.pay.WXPay;
import com.mimi.core.wx.pay.WXPayUtil;
import com.mimi.vo.PayReturnVo;
import com.mimi.vo.TokenVo;
import com.mimi.vo.WxConfigVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

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
    private CacheManager cacheManager;
    @Autowired
    private WxAppService wxAppService;
    @Autowired
    private UserInfoUtil userInfoUtil;

    @Autowired
    private WXPay wxPay;

    @Value("${kd.wx.notify.url}")
    private String notifyUrl;

    private static String getsig(String noncestr,String jsapi_ticket,String timestamp,String url){
        String[] paramArr = new String[] { "jsapi_ticket=" + jsapi_ticket,
                "timestamp=" + timestamp, "noncestr=" + noncestr, "url=" + url };
        Arrays.sort(paramArr);
        // 将排序后的结果拼接成一个字符串
        String content = paramArr[0].concat("&"+paramArr[1]).concat("&"+paramArr[2])
                .concat("&"+paramArr[3]);
        String gensignature = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            // 对拼接后的字符串进行 sha1 加密
            log.info("拼接加密签名："+content);
            byte[] digest = md.digest(content.getBytes());
            gensignature = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // 将 sha1 加密后的字符串与 signature 进行对比
        if (gensignature != null) {
            return gensignature;// 返回signature
        } else {
            return "false";
        }
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * 将字节转换为十六进制字符串
     *
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
                'B', 'C', 'D', 'E', 'F' };
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];
        String s = new String(tempArr);
        return s;
    }

    @GetMapping("/getWxConfig")
    @ResponseBody
    public R<WxConfigVo> getWxConfig(String appId,String url){
        WxConfigVo wxConfigVo = new WxConfigVo();
        wxConfigVo.setAppId(appId);
        Date date = new Date();
        String timestamp = Long.toString((new Date().getTime()) / 1000);
        wxConfigVo.setTimeStamp(timestamp);
        wxConfigVo.setNonceStr(WXPayUtil.generateNonceStr());
        PublicAccount publicAccount = publicAccountService.getByAppId(appId);
        String token = wxAppService.getToken(publicAccount);
        String ticket = wxAppService.getTicket(token);
        wxConfigVo.setSignature(getsig(wxConfigVo.getNonceStr(),ticket,wxConfigVo.getTimeStamp(),url));
        log.info("签名结果: "+wxConfigVo.getSignature());
        return R.success(wxConfigVo);
    }

    @RequestMapping("/payAgentOrder")
    @ResponseBody
    public R<PayReturnVo> payAgentOrder(HttpServletRequest request,@RequestBody OrderAgent orderAgent) throws Exception {
        String token = request.getHeader(UserInterceptor.ACCESS_TOKEN);
        ShopCouponInst shopCouponInst = null;
        TokenVo tokenVo = (TokenVo) cacheManager.getValue(token);
        //TokenVo tokenVo = redisCache.getCacheObject(token);
//        TokenVo tokenVo = new TokenVo();
//        tokenVo.setOpenId("oeI4a6lElS00HRWEsnq7WB6GvZ14");
//        tokenVo.setToken("123456");
        String openId = tokenVo.getOpenId();
        log.info("openId : ["+openId+"]发起支付！");
        String couponInstId = orderAgent.getCouponInstId();

        if(StringUtils.isEmpty(orderAgent.getPricingId())){
            throw new RuntimeException("缺少参数定价ID！");
        }

        PayAccount payAccount = payAccountService.findMy();
        if(payAccount==null){
            throw new RuntimeException("该学校未配置商户号！");
        }
        log.info("学校ID: "+payAccount.getSchoolId());
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
        orderAgent.setFullAddress(orderAgent.getBuildName()+"-"+orderAgent.getRoomName());

        orderAgent.setUserName(userInfoUtil.getRealName());
        orderAgent.setMobile(userInfoUtil.getPhone());

        orderAgent.setMoney(money);
        boolean r = orderAgentService.save(orderAgent);
        if(!r){
            return R.error("保存运单失败!");
        }
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
        r = orderAgentService.updateById(orderAgent);
        if(!r){
            return R.error("更新运单状态失败!");
        }

        //发送消息
        log.info("再次签名，内容："+map2);
        String sign = WXPayUtil.generateSignature(map2, payAccount.getAppKey());
        map2.put("sign",sign);

        log.info("xml: "+WXPayUtil.mapToXml(map2));

        PayReturnVo result = new PayReturnVo();
        result.setTimestamp(timestamp);
        result.setNonceStr(map2.get("nonceStr"));
        result.setPkg(map2.get("package"));
        result.setSign(sign);
        result.setSignType("MD5");
        result.setPrePayId(prePayId);

        log.info("微信支付成功，返回预付单："+prePayId);
        return R.success(result);
    }

    private static final String IP_UTILS_FLAG = ",";
    private static final String UNKNOWN = "unknown";
    private static final String LOCALHOST_IP = "0:0:0:0:0:0:0:1";
    private static final String LOCALHOST_IP1 = "127.0.0.1";


    public static String getIpAddress(HttpServletRequest request) {
        String ip = null;
        try {
            //以下两个获取在k8s中，将真实的客户端IP，放到了x-Original-Forwarded-For。而将WAF的回源地址放到了 x-Forwarded-For了。
            ip = request.getHeader("X-Original-Forwarded-For");
            if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("X-Forwarded-For");
            }
            //获取nginx等代理的ip
            if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("x-forwarded-for");
            }
            if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            //兼容k8s集群获取ip
            if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
                if (LOCALHOST_IP1.equalsIgnoreCase(ip) || LOCALHOST_IP.equalsIgnoreCase(ip)) {
                    //根据网卡取本机配置的IP
                    InetAddress iNet = null;
                    try {
                        iNet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        log.error("getClientIp error: {}", e);
                    }
                    ip = iNet.getHostAddress();
                }
            }
        } catch (Exception e) {
            log.error("IPUtils ERROR ", e);
        }
        //使用代理，则获取第一个IP地址
        if (!StringUtils.isEmpty(ip) && ip.indexOf(IP_UTILS_FLAG) > 0) {
            ip = ip.substring(0, ip.indexOf(IP_UTILS_FLAG));
        }

        return ip;
    }

    public static String getIpAddress2(HttpServletRequest request) {
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
