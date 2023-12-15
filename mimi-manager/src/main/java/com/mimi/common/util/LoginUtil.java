package com.mimi.common.util;

import com.mimi.system.entity.LoginUser;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * @Description TODO
 * @Author RuKai
 * @Date 2023/12/7 15:02
 **/
@UtilityClass
public class LoginUtil {

    /**
     * 用户id为1的是超管账号
     * @return
     */
    public static Boolean isSuperAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser user = (LoginUser) authentication.getPrincipal();
        return user.getUser().getId().equals("1");
    }


    public static String getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser user = (LoginUser) authentication.getPrincipal();
        return (String) Optional.ofNullable(user).map((o) -> {
            return o.getUser().getId();
        }).orElse(null);
    }


    public static String getSchoolId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser user = (LoginUser) authentication.getPrincipal();
        return (String) Optional.ofNullable(user).map((o) -> {
            return o.getUser().getSchoolId();
        }).orElse(null);
    }



}
