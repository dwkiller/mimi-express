package com.mimi.core.wx;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.mimi.core.common.superpackage.redis.CacheManager;
import com.mimi.core.express.entity.config.PublicAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class WxAppService {

    private static final String TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

    private static final String TICKET_URL="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";

//    @Autowired
//    private RedisCache redisCache;
    @Autowired
    private CacheManager cacheManager;

    public String getTicket(String token){
        String url = String.format(TICKET_URL,token);
        String rs = HttpUtil.get(url);
        JSONObject rsJo = JSONObject.parseObject(rs);
        if(rsJo.containsKey("errcode")&&rsJo.getInteger("errcode")!=0&&rsJo.containsKey("errmsg")){
            throw new RuntimeException("获取Ticket失败: "+rsJo.getString("errmsg"));
        }
        return rsJo.getString("ticket");
    }

    public String getToken(PublicAccount publicAccount){
        String schoolId=publicAccount.getSchoolId();
        String key="getToken_"+schoolId;
        String token = null;
        if(!cacheManager.exists(key)){
            String url = String.format(TOKEN_URL,publicAccount.getAppId(),publicAccount.getAppSecret());
            String rs = HttpUtil.get(url);
            log.info("获取token结果："+rs);
            JSONObject rsJo = JSONObject.parseObject(rs);
            if(rsJo.containsKey("errcode")){
                throw new RuntimeException("获取token失败: "+rsJo.getString("errmsg"));
            }
            token = rsJo.getString("access_token");
            int expiresIn = rsJo.getInteger("expires_in")-60;
            cacheManager.setValue(key,token,expiresIn);
            //redisCache.setCacheObject(key,token,expiresIn,TimeUnit.SECONDS);
        }else{
            //token = redisCache.getCacheObject(key);
            token = (String) cacheManager.getValue(key);
            log.info("cache token : "+token);
        }
        return token;
    }

    public String getToken2(PublicAccount publicAccount){
        String schoolId=publicAccount.getSchoolId();
        String key="getToken_"+schoolId;
        String token = (String) cacheManager.lock("",()->{
            String result = null;
            if(!cacheManager.exists(key)){
                String url = String.format(TOKEN_URL,publicAccount.getAppId(),publicAccount.getAppSecret());
                String rs = HttpUtil.get(url);
                log.info("获取token结果："+rs);
                JSONObject rsJo = JSONObject.parseObject(rs);
                if(rsJo.containsKey("errcode")){
                    throw new RuntimeException("获取token失败: "+rsJo.getString("errmsg"));
                }
                result = rsJo.getString("access_token");
                int expiresIn = rsJo.getInteger("expires_in")-600;
                cacheManager.setValue(key,result,expiresIn);
                //redisCache.setCacheObject(key,token,expiresIn,TimeUnit.SECONDS);
            }else{
                //token = redisCache.getCacheObject(key);
                result = (String) cacheManager.getValue(key);
                log.info("cache token : "+result);
            }
            return result;
        });
        return token;
    }

    public static void main(String[] args){
        String url = String.format(TOKEN_URL,"wx89a0fa9b59731bed","2aafba733dec682593304d0a11d3cf84");
        String rs = HttpUtil.get(url);
        System.out.println(rs);
    }
}
