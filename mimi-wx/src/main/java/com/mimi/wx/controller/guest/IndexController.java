package com.mimi.wx.controller.guest;


import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.mimi.core.common.R;
import com.mimi.core.common.util.RedisCache;
import com.mimi.core.express.entity.config.PublicAccount;
import com.mimi.core.express.entity.user.User;
import com.mimi.core.express.service.PublicAccountService;
import com.mimi.core.express.service.UserService;
import com.mimi.vo.TokenVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@Slf4j
@Tag(name = "用户管理")
@RestController
@RequestMapping("/guest/index")
public class IndexController {

    @Autowired
    private UserService userService;

    @Autowired
    private PublicAccountService publicAccountService;

    @Value("${kd.wx.code2Session.url}")
    private String code2SessionUrl;

    @Autowired
    private RedisCache redisCache;

    @GetMapping("/test")
    public R<String> test(){
        return R.success("test");
    }

    @PostMapping("/regist")
    public R<String> regist(@RequestBody User user){
        TokenVo tokenVo = getTokenBySchoolId(user.getSchoolId(),user.getAuthCode());
        user.setOpenId(tokenVo.getOpenId());
        user.setId(user.getMobile());
        userService.save(user);
        tokenVo.setUserId(user.getId());
        tokenVo.setPhone(user.getMobile());
        tokenVo.setRealName(user.getUserName());
        tokenVo.setSchoolId(user.getSchoolId());
        redisCache.setCacheObject(tokenVo.getToken(),tokenVo,tokenVo.getExpiresIn(), TimeUnit.SECONDS);
        return R.success(tokenVo.getToken());
    }

    @PostMapping("/refreshToken")
    public R<TokenVo> refreshToken(String schoolId, String authCode){
        TokenVo tokenVo = getToken(schoolId,authCode);
        if(tokenVo==null){
            return R.unRegist();
        }
        return R.success(tokenVo);
    }

    private TokenVo getToken(String schoolId,String authCode){
        TokenVo tokenVo = getTokenBySchoolId(schoolId,authCode);
        User user = userService.findByOpenId(tokenVo.getOpenId());
        if(user==null|| StringUtils.isEmpty(user.getSchoolId())){
            return null;
        }

        tokenVo.setUserId(user.getId());
        tokenVo.setPhone(user.getMobile());
        tokenVo.setRealName(user.getUserName());
        tokenVo.setSchoolId(user.getSchoolId());
        redisCache.setCacheObject(tokenVo.getToken(),tokenVo,tokenVo.getExpiresIn(), TimeUnit.SECONDS);
        return tokenVo;
    }

    private TokenVo getTokenBySchoolId(String schoolId,String authCode){
        PublicAccount publicAccount = publicAccountService.getBySchoolId(schoolId);
        if(publicAccount==null){
            throw new RuntimeException("该学校未绑定公众号！");
        }
        String url = String.format(code2SessionUrl,publicAccount.getAppId(),publicAccount.getAppSecret(),authCode );
        String strRs = HttpUtil.get(url);
        JSONObject jo = JSONObject.parseObject(strRs);
        if(jo.containsKey("errcode")){
            throw new RuntimeException("获取OPENID失败: 错误码:"+jo.getString("errcode")+", 内容: "+jo.getString("errmsg"));
        }
        TokenVo tokenVo = new TokenVo();
        tokenVo.setToken(jo.getString("access_token"));
        tokenVo.setOpenId(jo.getString("openid"));
        tokenVo.setExpiresIn(jo.getInteger("expires_in"));
        return tokenVo;
    }
}
