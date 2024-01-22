package com.mimi.core.message;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.mimi.core.common.util.UserInfoUtil;
import com.mimi.core.express.entity.config.MsgVariable;
import com.mimi.core.express.entity.config.NoticeTemp;
import com.mimi.core.express.entity.config.PublicAccount;
import com.mimi.core.express.entity.order.BaseOrder;
import com.mimi.core.express.entity.user.User;
import com.mimi.core.express.service.MsgVariableService;
import com.mimi.core.express.service.NoticeTempService;
import com.mimi.core.express.service.PublicAccountService;
import com.mimi.core.express.service.UserService;
import com.mimi.core.express.type.InnerVariable;
import com.mimi.core.wx.WxService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.Jar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class MessageService<T extends BaseOrder> {

    @Autowired
    private UserService userService;

    @Autowired
    private PublicAccountService publicAccountService;

    @Autowired
    private NoticeTempService noticeTempService;

    @Autowired
    private MsgVariableService msgVariableService;

    @Autowired
    private WxService wxService;

    @Autowired
    private UserInfoUtil userInfoUtil;

    public void sendMsg(String templateId, T order, Map<String,String> sendParam) throws WxErrorException {
        if(StringUtils.isEmpty(order.getMobile())){
            throw new RuntimeException("运单号上没有手机号码，无法确定用户！");
        }
        User user =userService.findByMobile(order.getMobile());
        if(user==null){
            throw new RuntimeException("根据手机号码["+order.getMobile()+"]不能找到用户！");
        }
        if(StringUtils.isEmpty(user.getOpenId())){
            throw new RuntimeException("该用户的公众号未注到系统！");
        }
        String schoolId=order.getSchoolId();
        PublicAccount publicAccount = publicAccountService.getBySchoolId(schoolId);
        if(publicAccount==null){
            throw new RuntimeException("该学校未绑定公众号！");
        }

        String callBackUrl = null;
        ISendMsgExt sendMsgExt=null;
        NoticeTemp noticeTemp = noticeTempService.findByTemplateId(templateId);
        if(noticeTemp!=null&&!StringUtils.isEmpty(noticeTemp.getUrl())){
            callBackUrl = noticeTemp.getUrl();
        }
        if(!StringUtils.isEmpty(noticeTemp.getSendPoint())){
            sendMsgExt = SpringUtil.getBean(noticeTemp.getSendPoint());
        }

        String token = wxService.getToken(publicAccount);
        List<MsgVariable> variableList = msgVariableService.findByTemplateId(templateId);

        WxMpServiceImpl wxMpService = new WxMpServiceImpl();
        WxMpDefaultConfigImpl wxStorage = new WxMpDefaultConfigImpl();
        wxStorage.setAppId(publicAccount.getAppId());
        wxStorage.setSecret(publicAccount.getAppSecret());
        wxStorage.setToken(token);
        wxMpService.setWxMpConfigStorage(wxStorage);

        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser(user.getOpenId())
                .templateId(templateId)
                .url(callBackUrl)
                .build();

        if(variableList!=null&&sendMsgExt!=null){
            for(MsgVariable msgVariable:variableList){
                if(!msgVariable.getType().equals("INNER")){
                    continue;
                }
                String value = "";
                if(StringUtils.isEmpty(msgVariable.getValue())){
                    continue;
                }else if(InnerVariable.CURRENT_TIME.getValue().equals(msgVariable.getValue())){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    value = sdf.format(new Date());
                }else if(InnerVariable.DATA_TIME.getValue().equals(msgVariable.getValue())){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    value = sdf.format(order.getCreateTime());
                }else if(InnerVariable.LOGIN_EMPLOYEE.getValue().equals(msgVariable.getValue())){
                    value = userInfoUtil.getRealName();
                }else if(InnerVariable.EMPLOYEE_MOBILE.getValue().equals(msgVariable.getValue())){
                    value = userInfoUtil.getPhone();
                }else if(InnerVariable.ORDER_NUMBER.getValue().equals(msgVariable.getValue())){
                    value = order.getOrderNum();
                }else{
                    value = sendMsgExt.parameterize(order,msgVariable);
                }
                templateMessage.addData(new WxMpTemplateData(msgVariable.getVariable(),value));
            }
        }
        if(sendParam!=null){
            Iterator<Map.Entry<String, String>> iterator = sendParam.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                templateMessage.addData(new WxMpTemplateData(entry.getKey(),entry.getValue()));
            }
        }
        wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);

        if(sendMsgExt!=null){
            sendMsgExt.execute(order);
        }
    }

    private static void getTemplate(){
        String token="76_4MNRd34xHx2TwSvlad14w-AnZKrvvZNYRub_jbNFnuhMB3Pm8m8MDaIcXiGX-o2tGsNmlNncL610gcsbTjRoLYhsz8G_kcdTdg1Rhy9BT-gYEe-22UIM9BHsrrUWYUaAIACEA";
        String templateId="hi0OYKik0W0FuJp93vR-hOPbsGufC43yoeiu26dPQ70";
        String openId = "oeI4a6l1zF20hc9fLhQiqvrpqeBE";
        String url = "https://api.weixin.qq.com/crm-rest/msg/wxTemps?access_token="+token;
        JSONObject jo = new JSONObject();
        jo.put("templateId",templateId);
        String rs = HttpUtil.post(url,jo.toJSONString());
        System.out.println(rs);
    }

    private static void sendTest(){
        String token="76_4MNRd34xHx2TwSvlad14w-AnZKrvvZNYRub_jbNFnuhMB3Pm8m8MDaIcXiGX-o2tGsNmlNncL610gcsbTjRoLYhsz8G_kcdTdg1Rhy9BT-gYEe-22UIM9BHsrrUWYUaAIACEA";
        String templateId="hi0OYKik0W0FuJp93vR-hOPbsGufC43yoeiu26dPQ70";
        String openId = "oeI4a6l1zF20hc9fLhQiqvrpqeBE";

        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+token;
        JSONObject param = new JSONObject();
        param.put("template_id",templateId);
        param.put("touser",openId);
        param.put("url","http://weixin.qq.com/download");

        JSONObject data = new JSONObject();

        JSONObject jo1 = new JSONObject();
        jo1.put("value","111");
        JSONObject jo2 = new JSONObject();
        jo2.put("value","2222");
        JSONObject jo3 = new JSONObject();
        jo3.put("value","2024-01-15 00:00:00");
        JSONObject jo4 = new JSONObject();
        jo4.put("value","15274898348");
        JSONObject jo5 = new JSONObject();
        jo5.put("value","1234");
        data.put("character_string2",jo1);
        data.put("character_string3",jo2);
        data.put("time8",jo3);
        data.put("const16",jo4);
        data.put("thing4",jo5);
        param.put("data",data);

        String rs = HttpUtil.post(url,param.toJSONString());
        System.out.println(rs);
    }

    public static void main(String[] args) throws WxErrorException {



//        WxMpServiceImpl wxMpService = new WxMpServiceImpl();
//        WxMpDefaultConfigImpl wxStorage = new WxMpDefaultConfigImpl();
//        wxStorage.setAppId("wx89a0fa9b59731bed");
//        wxStorage.setSecret("2aafba733dec682593304d0a11d3cf84");
//        wxStorage.setToken(token);
//        wxMpService.setWxMpConfigStorage(wxStorage);
//
//        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
//                .toUser(openId)
//                .templateId(templateId)
//                .url("")
//                .build();
//        templateMessage.addData(new WxMpTemplateData("character_string2","1111"));
//        templateMessage.addData(new WxMpTemplateData("character_string3","2222"));
//        templateMessage.addData(new WxMpTemplateData("time8","2024-01-15 00:00:00"));
//        templateMessage.addData(new WxMpTemplateData("const16","15274898348"));
//        templateMessage.addData(new WxMpTemplateData("thing4","1234"));
//
//        wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
    }
}
