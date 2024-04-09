package com.mimi.util.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;


@Slf4j
public class HttpAPIService {

	private RequestConfig config;

	private CloseableHttpClient sslHttpClient;

	public CloseableHttpClient getSslHttpClient() {
		return sslHttpClient;
	}


	public HttpAPIService(String appCert,String mchId,RequestConfig config) throws Exception {
		this.config = config;
		if(!StringUtils.isEmpty(appCert)&&!StringUtils.isEmpty(mchId)){
			initSSL(appCert,mchId);
		}
	}

	private void initSSL(String appCert,String mchId) throws Exception {
		if (sslHttpClient == null) {
			InputStream instream = null;
			KeyStore keyStore = null;
			try {
				keyStore = KeyStore.getInstance("PKCS12");
				instream = new FileInputStream(new File(appCert));
				keyStore.load(instream, mchId.toCharArray());
				log.info("证书加载完成");
			} catch (Exception e) {
				e.printStackTrace();
				log.error("获取证书失败: " + e.getMessage());
				throw e;
			} finally {
				if (instream != null) {
					instream.close();
				}
			}
			log.info("创建 SSLContext.....");
			SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mchId.toCharArray()).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
					sslcontext,
					new String[]{"TLSv1"},
					null,
					SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
			log.info("创建 SSLHttpClient.....");
			sslHttpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		}
	}

	public CloseableHttpResponse doGetResponse(String url) throws Exception {
		HttpGet httpGet = new HttpGet(url);         
		// 装载配置信息        
		httpGet.setConfig(config);         
		// 发起请求        
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		int reTry=0;
		while(reTry<3) {
			try {
				response = httpClient.execute(httpGet);
				reTry=3;
			}catch(ConnectionPoolTimeoutException e) {
				log.error("链接超时:  "+e.getMessage()+" 重试次数:"+reTry);
				reTry++;
			}
		}

		return response;
	}
	
	public String doGet(String url) throws Exception {
		// 声明 http get 请求        
		HttpGet httpGet = new HttpGet(url);         
		// 装载配置信息        
		httpGet.setConfig(config);         
		// 发起请求        
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		int reTry=0;
		while(reTry<3) {
			try {
				response = httpClient.execute(httpGet);
				reTry=3;
			}catch(ConnectionPoolTimeoutException e) {
				log.error("链接超时:  "+e.getMessage()+" 重试次数:"+reTry);
				reTry++;
			}
		}
		
		// 判断状态码是否为200        
		if (response.getStatusLine().getStatusCode() == 200) {
			// 返回响应体的内容           
			return EntityUtils.toString(response.getEntity(), "UTF-8");
		}

		System.out.println(response.getStatusLine().getStatusCode()+"----------"+EntityUtils.toString(response.getEntity(), "UTF-8"));
		return null;
	}
	
	public String doGet(String url, Map<String, Object> map) throws Exception {
		URIBuilder uriBuilder = new URIBuilder(url);         
		if (map != null) {            
			// 遍历map,拼接请求参数            
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
			}        
		}         
		// 调用不带参数的get请求
		return this.doGet(uriBuilder.build().toString());
	}
	
	public HttpResult doPostJson(String url,Map<String, Object> param,String contentType) throws Exception {
		HttpPost httpPost = new HttpPost(url);
		// 加入配置信息        
		httpPost.setConfig(config);
		httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json");

		String body = JSONObject.toJSONString(param);
		log.info("发送请求url: "+url);
		log.info("发送请求contentType: "+contentType);
		log.info("发送请求参数: "+body);
		StringEntity se = new StringEntity(body);
		se.setContentType(contentType);
        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"UTF-8"));
        httpPost.setEntity(se);
		CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = httpClient.execute(httpPost);
        return new HttpResult(response.getStatusLine().getStatusCode(), response.getEntity().getContent());
	}
	
	public HttpResult doPost(String url,Map<String, Object> map,Map<String,String> header) throws Exception {
		// 声明httpPost请求        
		HttpPost httpPost = new HttpPost(url);
		// 加入配置信息        
		httpPost.setConfig(config);
		
		if(header!=null) {
			Iterator<String> iter = header.keySet().iterator();
			while(iter.hasNext()) {
				String key = iter.next();
				httpPost.setHeader(key,header.get(key));
			}
		}
		
		// 判断map是否为空，不为空则进行遍历，封装from表单对象
		if (map != null) {
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				list.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
			}            
			// 构造from表单对象            
			UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list, "UTF-8");
			// 把表单放到post里            
			httpPost.setEntity(urlEncodedFormEntity);
		}
		// 发起请求
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = httpClient.execute(httpPost);
		return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity(), "UTF-8"));
	}
	
	public HttpResult doPost(String url, Map<String, Object> map) throws Exception {
		// 声明httpPost请求        
		HttpPost httpPost = new HttpPost(url);
		// 加入配置信息        
		httpPost.setConfig(config);
		// 判断map是否为空，不为空则进行遍历，封装from表单对象
		if (map != null&&map.size()>0) {
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				list.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
			}            
			// 构造from表单对象            
			UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list, "UTF-8");
			// 把表单放到post里            
			httpPost.setEntity(urlEncodedFormEntity);
		}         
		// 发起请求
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = httpClient.execute(httpPost);
		return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity(), "UTF-8"));
	}
	
	public HttpResult doSslPost(String url, String requestMethod, String outputStr) throws Exception{
		HttpPost httpPost = new HttpPost(url);
		// 加入配置信息        
		httpPost.setConfig(config);
		StringEntity postEntity = new StringEntity(outputStr, requestMethod);
		httpPost.setEntity(postEntity);
		
		CloseableHttpResponse response = this.sslHttpClient.execute(httpPost);
		return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity(), requestMethod));
	}
	
	public HttpResult doPost(String url, String requestMethod, String outputStr) throws Exception{
		// 声明httpPost请求        
		HttpPost httpPost = new HttpPost(url);
		// 加入配置信息        
		httpPost.setConfig(config);
		StringEntity postEntity = new StringEntity(outputStr, requestMethod);
		httpPost.setEntity(postEntity);

		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = httpClient.execute(httpPost);
		return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity(), requestMethod));
	}
	
	public HttpResult doPost(String url, String outputStr) throws Exception{
		// 声明httpPost请求        
		HttpPost httpPost = new HttpPost(url);
		// 加入配置信息        
		httpPost.setConfig(config);
		StringEntity postEntity = new StringEntity(outputStr);
		httpPost.setEntity(postEntity);

		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = httpClient.execute(httpPost);
		return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity()));
	}
	
	public HttpResult doPost(String url) throws Exception {
		return this.doPost(url, new HashMap<String,Object>());    
	}	
	
	

}
