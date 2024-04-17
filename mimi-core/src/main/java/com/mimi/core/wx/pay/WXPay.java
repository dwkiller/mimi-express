package com.mimi.core.wx.pay;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.mimi.core.express.entity.config.PayAccount;
import com.mimi.core.express.entity.config.PublicAccount;
import com.mimi.core.express.service.PublicAccountService;
import com.mimi.core.util.MapHelper;
import com.mimi.core.util.http.HttpAPIService;
import com.mimi.core.util.http.HttpResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WXPay {

	@Value("${kd.wx.unifiedorder.url}")
	private String unifiedorderUrl;
	@Value("${kd.wx.refund.url}")
	private String refundUrl;

	@Value("${kd.pay.fileroot}")
	private String fileRoot;

	@Autowired
	private PublicAccountService  publicAccountService;

	@Autowired
	private RequestConfig config;

	private Map<String, HttpAPIService> httpMap = new HashMap<>();

	private HttpAPIService getHttpAPIService(String appCert,String mchId) throws Exception {
		HttpAPIService httpAPIService = httpMap.get(mchId);
		if(httpAPIService==null){
			httpAPIService = new HttpAPIService(fileRoot+ File.separator+appCert,mchId,config);
			httpMap.put(mchId,httpAPIService);
		}
		return httpAPIService;
	}
	
	public Map<String,Object> refund(PayAccount payAccount, Map param) throws Exception {

		PublicAccount publicAccount = publicAccountService.getBySchoolId(payAccount.getSchoolId());

		Map<String, String> params = new HashMap<String, String>();
    	//小程序appID
		params.put("appid", publicAccount.getAppId());
		//商户号
		params.put("mch_id", payAccount.getMchId());
		//随机字符串 
		String desc = MapHelper.getStringValue(param, "refundDesc");
		if(desc!=null) {
			params.put("refund_desc", desc);
		}
		params.put("nonce_str", WXPayUtil.generateNonceStr());
		params.put("out_trade_no", MapHelper.getStringValue(param, "orderNum"));
		params.put("out_refund_no", MapHelper.getStringValue(param, "reOrderNum"));
		params.put("total_fee", MapHelper.getStringValue(param, "money"));
		params.put("refund_fee", MapHelper.getStringValue(param, "money"));
		String sign = WXPayUtil.generateSignature(params, payAccount.getAppKey());
		params.put("sign", sign);
		String requestString = WXPayUtil.getRequestXml(params);
		log.info("退单请求参数 :" + requestString);
		HttpAPIService httpAPIService = getHttpAPIService(payAccount.getFile(),payAccount.getMchId());
		HttpResult responseResult = httpAPIService.doSslPost(refundUrl, "UTF-8", requestString);
		log.info("退单返回参数 :" + responseResult.getBody());
		Map responseMap = WXPayUtil.xmlToMap(responseResult.getBody());
		log.debug(responseMap.toString());
		
		if(responseMap.get("result_code")!=null&&responseMap.get("err_code")!=null&&responseMap.get("err_code_des")!=null) {
			String resultCode = responseMap.get("result_code").toString();
			if(resultCode.equals("FAIL")) {
				/*
				FailPayLog fpLog = new FailPayLog();
				fpLog.setErrCode(responseMap.get("err_code").toString());
				fpLog.setErrDesc(responseMap.get("err_code_des").toString());
				fpLog.setOrderNumber(params.get("out_trade_no"));
				fpLog.setReOrderNumber(params.get("out_refund_no"));
				fpLog.setRefundCust(Double.parseDouble(params.get("refund_fee")));
				fpLog.setTotalCust(Double.parseDouble(params.get("total_fee")));
				commLogService.saveFailPayLog(fpLog);
				*/
			}
		}
		return responseMap;
	}
	
    /**
     * 作用：统一下单<br>
     * 场景：公共号支付、扫码支付、APP支付
     * @return API返回数据
     * @throws Exception
     */
    public Map<String,Object> unifiedOrder(PayAccount payAccount,Map param) throws Exception {
		PublicAccount publicAccount = publicAccountService.getBySchoolId(payAccount.getSchoolId());
		Map<String, String> params = new HashMap<String, String>();
    	//小程序appID
		params.put("appid", publicAccount.getAppId());
		//商户号
		params.put("mch_id", payAccount.getMchId());
		//随机字符串
		params.put("nonce_str", WXPayUtil.generateNonceStr());
		//商品描述
		params.put("body", "代取支付");
		//商户订单号
		params.put("out_trade_no", MapHelper.getStringValue(param, "orderNum"));
		params.put("total_fee", MapHelper.getStringValue(param, "money"));
		//终端IP  APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。
		params.put("spbill_create_ip", String.valueOf(param.get("spbill_create_ip")));
		//通知地址
		params.put("notify_url", String.valueOf(param.get("notify_url")));
		//交易类型
		params.put("trade_type", "JSAPI");
		//openid
		params.put("openid", String.valueOf(param.get("openid")));
		//附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用
		params.put("attach", "flag=true");
		String sign = WXPayUtil.generateSignature(params, payAccount.getAppKey());
		params.put("sign", sign);
		String requestString = WXPayUtil.getRequestXml(params);
		log.info("支付统一下单请求参数 :" + requestString);

		HttpAPIService httpAPIService = getHttpAPIService(payAccount.getFile(),payAccount.getMchId());
		HttpResult responseResult = httpAPIService.doPost(unifiedorderUrl, "UTF-8", requestString);
		log.info("支付统一下单返回参数 :" + responseResult.getBody());
		Map responseMap = WXPayUtil.xmlToMap(responseResult.getBody());
		
		return responseMap;
    }
    
    public static void main(String[] args) {
    	BigDecimal money = new BigDecimal(0.01).setScale(2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
    	System.out.println(money.intValue());
    }
}
