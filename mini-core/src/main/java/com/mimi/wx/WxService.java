package com.mimi.wx;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.mimi.common.util.RedisCache;
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
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class WxService {

    private static final String TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

    @Autowired
    private PublicAccountService publicAccountService;

    @Autowired
    private MsgVariableService msgVariableService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private UserService userService;

    @Autowired
    private NoticeTempService noticeTempService;



    private String getToken(PublicAccount publicAccount){
        String schoolId=publicAccount.getSchoolId();
        String key="getToken_"+schoolId;
        String token = redisCache.getCacheObject(key);
        if(token==null){
            String url = String.format(TOKEN_URL,publicAccount.getAppId(),publicAccount.getAppSecret());
            String rs = HttpUtil.get(url);
            JSONObject rsJo = JSONObject.parseObject(rs);
            if(rsJo.containsKey("errcode")){
                throw new RuntimeException("获取token失败: "+rsJo.getString("errmsg"));
            }
            token = rsJo.getString("access_token");
            int expiresIn = rsJo.getInteger("expires_in");
            redisCache.setCacheObject(key,token,expiresIn,TimeUnit.SECONDS);
        }
        return token;
    }

    public <O extends BaseOrder>void sendMsg(String templateId,O order,WxMessageParameterize wxMessageParameterize) throws WxErrorException {
        if(StringUtils.isEmpty(order.getMobile())){
            throw new RuntimeException("运单号上没有手机号码，无法确定用户！");
        }
        User user =userService.findByMobile(order.getMobile());
        if(user==null){
            throw new RuntimeException("根据手机号码["+order.getMobile()+"]不能找到用户！");
        }
        if(StringUtils.isEmpty(user.getGzhOpenId())){
            throw new RuntimeException("该用户的公众号未注到系统！");
        }
        String schoolId=order.getSchoolId();
        PublicAccount publicAccount = publicAccountService.getBySchoolId(schoolId);
        if(publicAccount==null){
            throw new RuntimeException("该学校未绑定公众号！");
        }
        String callBackUrl = null;
        NoticeTemp noticeTemp = noticeTempService.findByTemplateId(templateId);
        if(noticeTemp!=null&&!StringUtils.isEmpty(noticeTemp.getUrl())){
            callBackUrl = noticeTemp.getUrl();
        }

        String token = getToken(publicAccount);
        List<MsgVariable> variableList = msgVariableService.findByTemplateId(templateId);

        WxMpServiceImpl wxMpService = new WxMpServiceImpl();
        WxMpDefaultConfigImpl wxStorage = new WxMpDefaultConfigImpl();
        wxStorage.setAppId(publicAccount.getAppId());
        wxStorage.setSecret(publicAccount.getAppSecret());
        wxStorage.setToken(token);
        wxMpService.setWxMpConfigStorage(wxStorage);

        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser(user.getGzhOpenId())
                .templateId(templateId)
                .url(callBackUrl)
                .build();

        if(variableList!=null&&wxMessageParameterize!=null){
            for(MsgVariable msgVariable:variableList){
                String value = wxMessageParameterize.parameterize(msgVariable);
                templateMessage.addData(new WxMpTemplateData(msgVariable.getVariable(),value));
            }
        }
        wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
    }

}
