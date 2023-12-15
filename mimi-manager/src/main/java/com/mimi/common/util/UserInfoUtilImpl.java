package com.mimi.common.util;

import com.mimi.common.util.UserInfoUtil;
import org.springframework.stereotype.Component;

@Component
public class UserInfoUtilImpl implements UserInfoUtil {

    @Override
    public String getUserId() {
        return LoginUtil.getUserId();
    }

    @Override
    public String getSchoolId() {
        return LoginUtil.getSchoolId();
    }
}
