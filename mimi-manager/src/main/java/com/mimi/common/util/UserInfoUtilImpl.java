package com.mimi.common.util;

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

    @Override
    public String getPhone() {
        return LoginUtil.getPhone();
    }

    @Override
    public String getRealName() {
        return LoginUtil.getRealName();
    }
}
