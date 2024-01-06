package com.mimi.wx;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.mimi.common.util.RedisCache;
import com.mimi.common.util.UserInfoUtil;
import com.mimi.express.entity.config.MsgVariable;
import com.mimi.express.entity.config.PublicAccount;
import com.mimi.express.entity.order.BaseOrder;
import com.mimi.express.service.MsgVariableService;
import com.mimi.express.service.PublicAccountService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class WxService {

    private static final String TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

    private static final String SEND_MSG_URL="https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token=%s";

    @Autowired
    private PublicAccountService publicAccountService;

    @Autowired
    private MsgVariableService msgVariableService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private UserInfoUtil userInfoUtil;

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

    public <O extends BaseOrder>void sendMsg(String templateId,O order,WxMessageParameterize wxMessageParameterize){
        String schoolId=userInfoUtil.getSchoolId();
        PublicAccount publicAccount = publicAccountService.getBySchoolId(schoolId);
        String token = getToken(publicAccount);
        List<MsgVariable> variableList = msgVariableService.findByTemplateId(templateId);

        WxMpServiceImpl wxMpService = new WxMpServiceImpl();
        WxMpDefaultConfigImpl wxStorage = new WxMpDefaultConfigImpl();
        wxStorage.setAppId(publicAccount.getAppId());
        wxStorage.setSecret(publicAccount.getAppSecret());
        wxMpService.setWxMpConfigStorage(wxStorage);

        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser("userOpenId")
                .templateId("yourTemplateId")
                .url("http://www.example.com/callback")
                .build();

        Map<String,Object> param = new HashMap<>();
        if(variableList!=null&&wxMessageParameterize!=null){
            for(MsgVariable msgVariable:variableList){
                Object value = wxMessageParameterize.parameterize(msgVariable,order);
            }
        }

        String url = String.format(SEND_MSG_URL,token);

    }

}
