package com.mimi.wx.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.mimi.common.R;
import com.mimi.common.util.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Component
public class UserInterceptor implements HandlerInterceptor {

    public static final String ACCESS_TOKEN="token";

    private String charset="UTF-8";

    @Autowired
    private RedisCache redisCache;

    private void write(HttpServletResponse response, R responseObject) throws IOException {
        String rs = JSONObject.toJSONString(responseObject);
        response.setCharacterEncoding(charset);
        response.setContentType("application/json; charset"+charset.toLowerCase());
        PrintWriter out = response.getWriter();
        out.append(rs);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
        log.info("request请求地址path["+request.getServletPath()+"] uri["+request.getRequestURI()+"]");
        String accessToken = request.getHeader(ACCESS_TOKEN);
        String openId = redisCache.getCacheObject(accessToken);
        if(StringUtils.isEmpty(openId)){
            write(response,R.unLogin());
            return false;
        }else{
            return true;
        }
    }
}
