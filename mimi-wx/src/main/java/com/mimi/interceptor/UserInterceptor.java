package com.mimi.interceptor;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSONObject;
import com.mimi.core.common.R;
import com.mimi.core.common.util.RedisCache;
import com.mimi.vo.TokenVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class UserInterceptor implements HandlerInterceptor {

    public static final String ACCESS_TOKEN="token";

    public static final String USER_INFO="user_info";

    private String charset="UTF-8";

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
        if(redisCache==null){
            redisCache = SpringUtil.getBean("redisCache");
        }
        TokenVo tokenVo = redisCache.getCacheObject(accessToken);
//        TokenVo tokenVo = new TokenVo();
//        tokenVo.setOpenId("oeI4a6lElS00HRWEsnq7WB6GvZ14");
//        tokenVo.setToken("123456");
//        tokenVo.setSchoolId("a5629f5c54f53dca61f21fdc190c929a");
        if(tokenVo==null){
            write(response,R.unLogin());
            return false;
        }else{
            request.setAttribute(USER_INFO,tokenVo);
            return true;
        }
    }
}
