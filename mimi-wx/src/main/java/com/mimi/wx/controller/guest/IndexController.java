package com.mimi.wx.controller.guest;


import cn.hutool.Hutool;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.mimi.core.common.R;
import com.mimi.core.common.superpackage.redis.CacheManager;
import com.mimi.core.express.entity.config.PublicAccount;
import com.mimi.core.express.entity.config.School;
import com.mimi.core.express.entity.user.User;
import com.mimi.core.express.service.PublicAccountService;
import com.mimi.core.express.service.SchoolService;
import com.mimi.core.express.service.UserService;
import com.mimi.core.util.ALiYunSMSUtil;
import com.mimi.vo.CheckCodeVo;
import com.mimi.vo.TokenVo;
import com.mimi.vo.UserVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.Pattern;


@Slf4j
@Tag(name = "用户管理")
@RestController
@RequestMapping("/guest/index")
public class IndexController {

    public static final String CHEKC_CODE="checkCode:";

    @Autowired
    private UserService userService;

    @Autowired
    private PublicAccountService publicAccountService;

    @Value("${kd.wx.code2Session.url}")
    private String code2SessionUrl;

    @Value("${kd.regist.checkcode:SMS_286280162}")
    private String registCheckCode;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private ALiYunSMSUtil aLiYunSMSUtil;

    @Autowired
    private SchoolService schoolService;

    @GetMapping("/test")
    public R<String> test(){
        return R.success("test");
    }

    @PostMapping("/getCheckCode")
    public R<?> getCheckCode(@RequestBody CheckCodeVo checkCodeVo){
        String mobile = checkCodeVo.getMobile();
        String key = CHEKC_CODE+mobile;
        if(cacheManager.exists(key)){
            return R.error("请不要频繁的发送验证码!");
        }
        String code = RandomUtil.randomNumbers(6);
        JSONObject param = new JSONObject();
        param.put("code",code);
        aLiYunSMSUtil.sendSMS(registCheckCode,mobile,param);
        cacheManager.setValue(key,code,300);
        return R.success();
    }


    @PostMapping("/regist")
    public R<TokenVo> regist(@RequestBody UserVo userVo){
        if(StringUtils.isEmpty(userVo.getCheckCode())){
            return R.error("请填写短信验证码!");
        }
        String key = CHEKC_CODE+userVo.getUser().getMobile();
        if(!cacheManager.exists(key)){
            return R.error("您的验证码已失效，请重新发送验证码！");
        }
        String checkCode = (String) cacheManager.getValue(key);
        if(!userVo.getCheckCode().equals(checkCode)){
            return R.error("验证码错误！");
        }

        TokenVo tokenVo = userVo.getTokenVo();
        User user = userVo.getUser();

        if(userService.findByMobileAndSchool(user.getMobile(),tokenVo.getSchoolId())!=null){
            return R.error("该电话号码["+user.getMobile()+"]已经存在");
        }

        user.setOpenId(tokenVo.getOpenId());
        user.setSchoolId(tokenVo.getSchoolId());
        userService.save(user);
        tokenVo.setUserId(user.getId());
        tokenVo.setPhone(user.getMobile());
        tokenVo.setSchoolYear(user.getSchoolYear());
        tokenVo.setRealName(user.getUserName());
        PublicAccount publicAccount = publicAccountService.getBySchoolId(tokenVo.getSchoolId());
        if(publicAccount!=null){
            tokenVo.setAppId(publicAccount.getAppId());
        }
        tokenVo.setRsCode((short)1);
        //redisCache.setCacheObject(tokenVo.getToken(),tokenVo,tokenVo.getExpiresIn(), TimeUnit.SECONDS);
        cacheManager.setValue(tokenVo.getToken(),tokenVo,tokenVo.getExpiresIn());
        return R.success(tokenVo);
    }

    @PostMapping("/refreshToken")
    public R<TokenVo> refreshToken(@RequestBody User user){
        log.info("refreshToken pubId: "+user.getPubId()+" ; authCode: "+user.getAuthCode()+" ; token: "+user.getToken());
        TokenVo tokenVo = getToken(user.getPubId(),user.getAuthCode(),user.getToken());
        return R.success(tokenVo);
    }

    private TokenVo getToken(String pubId,String authCode,String token){
        TokenVo tokenVo = null;
        if(!StringUtils.isEmpty(token)){
            //tokenVo = redisCache.getCacheObject(token);
            tokenVo = (TokenVo) cacheManager.getValue(token);
            log.info("获取到token: "+token);
        }else{
            log.info("无token");
        }
        if(tokenVo==null){
            log.info("缓存不存在token");
            tokenVo = getTokenByPubId(pubId,authCode);
            log.info("获取微信端openId:"+tokenVo.getOpenId());
            User user = userService.findByOpenId(tokenVo.getOpenId());
            if(user==null|| StringUtils.isEmpty(user.getSchoolId())){
                tokenVo.setRsCode((short)0);
                return tokenVo;
            }
            tokenVo.setUserId(user.getId());
            tokenVo.setPhone(user.getMobile());
            tokenVo.setSchoolYear(user.getSchoolYear());
            tokenVo.setRealName(user.getUserName());
            tokenVo.setRsCode((short)1);
            cacheManager.setValue(tokenVo.getToken(),tokenVo,tokenVo.getExpiresIn());
        }
        return tokenVo;
    }

    private TokenVo getTokenByPubId(String pubId,String authCode){
        PublicAccount publicAccount = publicAccountService.getByAppId(pubId);
        if(publicAccount==null){
            throw new RuntimeException("不存在该公众号:"+pubId);
        }
        School school = schoolService.getById(publicAccount.getSchoolId());
        if(school==null){
            throw new RuntimeException("该公众号未绑定学校！");
        }
        String url = String.format(code2SessionUrl,publicAccount.getAppId(),publicAccount.getAppSecret(),authCode );
        String strRs = HttpUtil.get(url);
        JSONObject jo = JSONObject.parseObject(strRs);
        if(jo.containsKey("errcode")){
            throw new RuntimeException("获取OPENID失败: 错误码:"+jo.getString("errcode")+", 内容: "+jo.getString("errmsg"));
        }
        TokenVo tokenVo = new TokenVo();
        tokenVo.setSchoolId(school.getId());
        tokenVo.setAppId(publicAccount.getAppId());
        tokenVo.setToken(jo.getString("access_token"));
        tokenVo.setOpenId(jo.getString("openid"));
        tokenVo.setExpiresIn(jo.getInteger("expires_in"));
        return tokenVo;
    }
}
