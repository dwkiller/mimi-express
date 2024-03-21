package com.mimi.util;

import com.mimi.core.common.util.UserInfoUtil;
import com.mimi.interceptor.UserInterceptor;
import com.mimi.vo.TokenVo;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Component
public class UserInfoUtilImpl implements UserInfoUtil {
    @Override
    public String getUserId() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        TokenVo tokenVo = (TokenVo) requestAttributes.getAttribute(UserInterceptor.USER_INFO,RequestAttributes.SCOPE_REQUEST);
        if(tokenVo==null){
            return null;
        }
        return tokenVo.getUserId();
    }

    @Override
    public String getSchoolId() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        TokenVo tokenVo = (TokenVo) requestAttributes.getAttribute(UserInterceptor.USER_INFO,RequestAttributes.SCOPE_REQUEST);
        if(tokenVo==null){
            return null;
        }
        return tokenVo.getSchoolId();
    }

    @Override
    public String getPhone() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        TokenVo tokenVo = (TokenVo) requestAttributes.getAttribute(UserInterceptor.USER_INFO,RequestAttributes.SCOPE_REQUEST);
        if(tokenVo==null){
            return null;
        }
        return tokenVo.getPhone();
    }

    @Override
    public String getRealName() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        TokenVo tokenVo = (TokenVo) requestAttributes.getAttribute(UserInterceptor.USER_INFO,RequestAttributes.SCOPE_REQUEST);
        if(tokenVo==null){
            return null;
        }
        return tokenVo.getRealName();
    }
}
