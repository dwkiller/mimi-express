package com.mimi.message;

import cn.hutool.extra.spring.SpringUtil;
import com.mimi.common.util.UserInfoUtil;
import com.mimi.express.entity.config.MsgVariable;
import com.mimi.express.entity.config.NoticeTemp;
import com.mimi.express.entity.config.PublicAccount;
import com.mimi.express.entity.order.BaseOrder;
import com.mimi.express.entity.user.User;
import com.mimi.express.service.MsgVariableService;
import com.mimi.express.service.NoticeTempService;
import com.mimi.express.service.PublicAccountService;
import com.mimi.express.service.UserService;
import com.mimi.express.type.InnerVariable;
import com.mimi.express.type.MsgSendPoint;
import com.mimi.wx.WxService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.apache.commons.lang3.StringUtils;
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
                if(StringUtils.isEmpty(msgVariable.getVariable())){
                    continue;
                }else if(InnerVariable.CURRENT_TIME.getValue().equals(msgVariable.getVariable())){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    value = sdf.format(new Date());
                }else if(InnerVariable.DATA_TIME.getValue().equals(msgVariable.getVariable())){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    value = sdf.format(order.getCreateTime());
                }else if(InnerVariable.LOGIN_EMPLOYEE.getValue().equals(msgVariable.getVariable())){
                    value = userInfoUtil.getRealName();
                }else if(InnerVariable.EMPLOYEE_MOBILE.getValue().equals(msgVariable.getVariable())){
                    value = userInfoUtil.getPhone();
                }else if(InnerVariable.ORDER_NUMBER.getValue().equals(msgVariable.getVariable())){
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
}
