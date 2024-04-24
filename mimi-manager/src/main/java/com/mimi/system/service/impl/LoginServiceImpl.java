package com.mimi.system.service.impl;

import com.mimi.core.common.superpackage.redis.CacheManager;
import com.mimi.core.system.entity.Employee;
import com.mimi.system.entity.LoginUser;
import com.mimi.system.service.LoginService;
import com.mimi.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Description TODO
 * @Author RuKai
 * @Date 2023/12/6 15:41
 **/
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;
//    @Autowired
//    private RedisCache redisCache;

    @Autowired
    private CacheManager cacheManager;

    //public static Map<String,Object> cache = new HashMap<>();

    @Override
    public Map<String,String> login(Employee user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        //使用userid生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //authenticate存入redis
        cacheManager.setValue("login:"+userId,loginUser,3600);
        //redisCache.setCacheObject("login:"+userId,loginUser);
        //cache.put("login:"+userId,loginUser);
        //把token响应给前端
        HashMap<String,String> map = new HashMap<>();
        map.put("token",jwt);
        return map;
    }

    @Override
    public String loginOut() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userid = loginUser.getUser().getId();
        cacheManager.remove("login:"+userid);
        //redisCache.deleteObject("login:"+userid);
        //cache.remove("login:"+userid);
        return "退出成功";
    }

}
