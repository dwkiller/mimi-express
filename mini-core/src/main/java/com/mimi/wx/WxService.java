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
import com.mimi.express.type.InnerVariable;
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

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class WxService {

    private static final String TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

    @Autowired
    private RedisCache redisCache;

    public String getToken(PublicAccount publicAccount){
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


}
