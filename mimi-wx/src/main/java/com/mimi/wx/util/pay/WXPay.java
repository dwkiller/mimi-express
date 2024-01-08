package com.mimi.wx.util.pay;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.net.ssl.SSLContext;

import com.mimi.wx.util.MapHelper;
import com.mimi.wx.util.http.HttpAPIService;
import com.mimi.wx.util.http.HttpResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WXPay {


	@Value("${wx.appId}")
	private String appId;
	@Value("${wx.apiKey}")
	private String apiKey;
	@Value("${wx.mchId}")
	private String mchId;
	@Value("${wx.unifiedorderUrl}")
	private String unifiedorderUrl;
	@Value("${wx.refund.url}")
	private String refundUrl;
	@Value("${weixin.app.cert}")
	private String appCert;
	@Value("${wx.promotion.transfers.url}")
	private String promotionTransfersUrl;
	
	@Autowired
	private HttpAPIService httpAPIService;
	
	private void initSSL() throws Exception {
		if(httpAPIService.getSslHttpClient()==null) {
			InputStream instream = null;
			KeyStore keyStore  = null;
			try {
				keyStore = KeyStore.getInstance("PKCS12");
				instream = new FileInputStream(new File(appCert));
				keyStore.load(instream, mchId.toCharArray());
				log.info("证书加载完成");
			} catch (Exception e) {
				e.printStackTrace();
				log.error("获取证书失败: "+e.getMessage());
				throw e;
			}finally {
				if(instream!=null) {
					instream.close();
				}
			}
			log.info("创建 SSLContext.....");
			SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore,mchId.toCharArray()).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
		            sslcontext,
		            new String[] { "TLSv1" },
		            null,
		            SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
			log.info("创建 SSLHttpClient.....");
			httpAPIService.setSslHttpClient(HttpClients.custom()
		            .setSSLSocketFactory(sslsf)
		            .build());
		}
	}
	
	public Map<String,Object> payToUser(CommissionOrder co) throws Exception{
		initSSL();
		BigDecimal money = new BigDecimal(co.getCust()).setScale(2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));//转换成分
		
		String addr = InetAddress.getLocalHost().getHostAddress();
		log.info(addr+"往客户打款:"+money.intValue()+"分 ");
		
		
		Map<String, String> params = new HashMap<String, String>();
    	//小程序appID
		params.put("mch_appid", appId);
		//商户号
		params.put("mch_id", mchId);
		params.put("nonce_str", WXPayUtil.generateNonceStr());
		params.put("partner_trade_no", co.getOrderNumber());
		params.put("openid", co.getOpenId());
		params.put("check_name", "NO_CHECK");
		params.put("amount", money.intValue()+"");
		params.put("desc", co.getMemo());
		params.put("spbill_create_ip", addr);
		String sign = WXPayUtil.generateSignature(params, apiKey);
		params.put("sign", sign);
		
		String requestString = WXPayUtil.getRequestXml(params);
		log.info("打款请求参数 :" + requestString);
		HttpResult responseResult = httpAPIService.doSslPost(promotionTransfersUrl, "UTF-8", requestString);
		log.info("打款返回参数 :" + responseResult.getBody());
		Map responseMap = WXPayUtil.xmlToMap(responseResult.getBody());
		log.debug(responseMap.toString());
		return responseMap;
	}
	
	public Map<String,Object> refund(Map param) throws Exception {
		
		initSSL();

		Map<String, String> params = new HashMap<String, String>();
    	//小程序appID
		params.put("appid", appId);
		//商户号
		params.put("mch_id", mchId);
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
		String sign = WXPayUtil.generateSignature(params, apiKey);
		params.put("sign", sign);
		String requestString = WXPayUtil.getRequestXml(params);
		log.info("退单请求参数 :" + requestString);
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
     * @param reqData 向wxpay post的请求数据
     * @return API返回数据
     * @throws Exception
     */
    public Map<String,Object> unifiedOrder(Map param) throws Exception {
		
    	Map<String, Object> result = new HashMap<String, Object>();
    	
		Map<String, String> params = new HashMap<String, String>();
    	//小程序appID
		params.put("appid", appId);
		//商户号
		params.put("mch_id", mchId);
		//随机字符串
		params.put("nonce_str", WXPayUtil.generateNonceStr());
		//商品描述
		params.put("body", "设备租赁");
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
		String sign = WXPayUtil.generateSignature(params, apiKey);
		params.put("sign", sign);
		String requestString = WXPayUtil.getRequestXml(params);
		log.info("支付统一下单请求参数 :" + requestString);
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
