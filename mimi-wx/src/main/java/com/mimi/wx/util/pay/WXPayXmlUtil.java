package com.mimi.wx.util.pay;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * 2018/7/3
 */
public final class WXPayXmlUtil {
    public static DocumentBuilder newDocumentBuilder() throws ParserConfigurationException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        documentBuilderFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        documentBuilderFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        documentBuilderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        documentBuilderFactory.setXIncludeAware(false);
        documentBuilderFactory.setExpandEntityReferences(false);

        return documentBuilderFactory.newDocumentBuilder();
    }

    public static Document newDocument() throws ParserConfigurationException {
        return newDocumentBuilder().newDocument();
    }
    
    public static Map xmlToMap(String xml) throws ParserConfigurationException, SAXException, IOException {
    	
    	Map result = new HashMap();
    	
    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    	 dbf.setNamespaceAware(true);
    	DocumentBuilder dBuilder = dbf.newDocumentBuilder();
    	Document document = dBuilder.parse(new InputSource(new StringReader(xml)));    
    	
    	Element element = document.getDocumentElement();
		NodeList list = element.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			result.put(node.getNodeName(), node.getFirstChild().getNodeValue());
		}

		return result;
    }
    
    public static void main(String[] args) {

		String xml = "<?xml version = '1.0' encoding = 'utf-8'?><xml><appid><![CDATA[wx2421b1c4370ec43b]]></appid><attach><![CDATA[支付测试]]></attach><bank_type><![CDATA[CFT]]></bank_type>"
		  +"<fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[Y]]></is_subscribe><mch_id><![CDATA[10000100]]></mch_id>"
		  +"<nonce_str><![CDATA[5d2b6c2a8db53831f7eda20af46e531c]]></nonce_str><openid><![CDATA[oUpF8uMEb4qRXf22hE3X68TekukE]]></openid>"
		  +"<out_trade_no><![CDATA[1409811653]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code>"
		  +"<return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[B552ED6B279343CB493C5DD0D78AB241]]></sign>"
		  +"<sub_mch_id><![CDATA[10000100]]></sub_mch_id><time_end><![CDATA[20140903131540]]></time_end>"
		  +"<total_fee>1</total_fee><coupon_fee><![CDATA[10]]></coupon_fee><coupon_count><![CDATA[1]]></coupon_count>"
		  +"<coupon_type><![CDATA[CASH]]></coupon_type><coupon_id><![CDATA[10000]]></coupon_id><coupon_fee><![CDATA[100]]></coupon_fee>"
		  +"<trade_type><![CDATA[JSAPI]]></trade_type><transaction_id><![CDATA[1004400740201409030005092168]]></transaction_id></xml>";
		
		try {
			Map result = xmlToMap(xml);
			System.out.println(result);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
