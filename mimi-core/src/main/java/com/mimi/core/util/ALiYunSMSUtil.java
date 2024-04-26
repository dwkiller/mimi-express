package com.mimi.core.util;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.mimi.core.util.http.HttpAPIService;
import com.ulisesbocchio.jasyptspringboot.encryptor.DefaultLazyEncryptor;
import com.ulisesbocchio.jasyptspringboot.encryptor.SimplePBEStringEncryptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class ALiYunSMSUtil {

	@Value("${ali.appId:111}")
	private String appId;
	@Value("${ali.appKey:111}")
	private String appKey;
	@Value("${ali.sms.smsUrl:http://dysmsapi.aliyuncs.com}")
	private String smsUrl="http://dysmsapi.aliyuncs.com";

	@Autowired
	private RequestConfig config;

	
	public Map sendSMS(String code,String mobile, JSONObject params) {
		log.info("发送短信");
		Map result = new HashMap();
		result.put("code", 1);
		result.put("desc", "success");
	    
	    java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	    df.setTimeZone(new java.util.SimpleTimeZone(0, "GMT"));// 这里一定要设置GMT时区
	    
	    Map<String, String> paras = new HashMap<String, String>();
	    
	    //系统参数
	    paras.put("SignatureMethod", "HMAC-SHA1");
	    paras.put("SignatureNonce", java.util.UUID.randomUUID().toString());
	    paras.put("AccessKeyId", appId);
	    paras.put("SignatureVersion", "1.0");
	    paras.put("Timestamp", df.format(new java.util.Date()));
	    paras.put("Format", "JSON");
	    
	    //业务API参数
	    paras.put("Action", "SendSms");
	    paras.put("Version", "2017-05-25");
	    paras.put("RegionId", "cn-hangzhou");

	    paras.put("PhoneNumbers", mobile);
	    paras.put("SignName", "叮叮快递");
	    paras.put("TemplateParam", params.toJSONString());
	    paras.put("TemplateCode", code);
	    paras.put("OutId", "123");
	    
	    if (paras.containsKey("Signature")) {
	        paras.remove("Signature");
	    }
	    
	    java.util.TreeMap<String, String> sortParas = new java.util.TreeMap<String, String>();
	    sortParas.putAll(paras);

        try {
		    java.util.Iterator<String> it = sortParas.keySet().iterator();
		    StringBuilder sortQueryStringTmp = new StringBuilder();
		    while (it.hasNext()) {
		        String key = it.next();
		        sortQueryStringTmp.append("&").append(specialUrlEncode(key)).append("=").append(specialUrlEncode(paras.get(key)));
		    }
		    String sortedQueryString = sortQueryStringTmp.substring(1);// 去除第一个多余的&符号
		    
		    StringBuilder stringToSign = new StringBuilder();
		    stringToSign.append("GET").append("&");
		    stringToSign.append(specialUrlEncode("/")).append("&");
		    stringToSign.append(specialUrlEncode(sortedQueryString));
		    String sign = sign(appKey + "&", stringToSign.toString());
		    
		    String Signature = specialUrlEncode(sign);// zJDF%2BLrzhj%2FThnlvIToysFRq6t4%3D
		    
		    String url = smsUrl + "/?Signature=" + Signature + sortQueryStringTmp;	 
		    log.info("短信url: "+ url);
			HttpAPIService httpAPIService = new HttpAPIService(config);
		    String httpResult = httpAPIService.doGet(url);
		    if (httpResult == null) {
				result.put("code", 0);
				result.put("desc", "调用短信接口异常");
				log.info("调用短信接口异常");
				return result;
			} else {
				log.info("短信接口返回信息：" + httpResult);
				JSONObject jo = JSONObject.parseObject(httpResult);
				if(jo.getString("Code") == null || !jo.getString("Code").equals("OK")) {
					result.put("code", 0);
					result.put("desc", "调用短信接口失败: "+jo.getString("Message"));
					log.info("调用短信接口失败: "+jo.getString("Message"));
					return result;
				}
				log.info("短信发送成功");
			}
        } catch (Exception e) {
        	log.error("发送短信异常，异常信息：" + e.getMessage());
			e.printStackTrace();
			result.put("code", 0);
			result.put("desc", e.getMessage());
			return result;
		}
	    return result;
	}
	
	//构造待签名的请求串
    public static String specialUrlEncode(String value) throws Exception {
        return java.net.URLEncoder.encode(value, "UTF-8").replace("+", "%20").replace("*", "%2A").replace("%7E", "~");
    }
    
    //签名
    public static String sign(String accessSecret, String stringToSign) throws Exception {
        javax.crypto.Mac mac = javax.crypto.Mac.getInstance("HmacSHA1");
        mac.init(new javax.crypto.spec.SecretKeySpec(accessSecret.getBytes("UTF-8"), "HmacSHA1"));
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        return new sun.misc.BASE64Encoder().encode(signData);
    }



    public static void main(String[] args){
		JSONObject param = new JSONObject();
		param.put("code","123456");
		ALiYunSMSUtil aLiYunSMSUtil = new ALiYunSMSUtil();
		Map result = aLiYunSMSUtil.sendSMS("SMS_286280162","15274898348",param);
		System.out.println(result);
	}
}
