package com.mimi.wx.controller.security;

import cn.hutool.core.lang.hash.Hash;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.mimi.core.common.R;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/guest/door")
@Tag(name = "门模块")
public class DoorController {


    @Value("${kd.door.appsecret}")
    private String appsecret;

    @Value("${kd.door.url:https://api.netrelay.cn/api/v1/control}")
    private String url;

    @GetMapping("/open/{code}")
    public R open(@PathVariable(value = "code") String code){
        String[] codes = code.split("_");
        if(codes.length!=2){
            return R.error("参数不合法:"+code);
        }
        String sn = codes[0];
        int locat = Integer.parseInt(codes[1]);
        String setr = "";
        for(int i=0;i<8;i++){
            if(i+1==locat){
                setr+="2";
            }else{
                setr+="x";
            }
        }
        Map<String,Object> param = new HashMap<>();
        param.put("appsecret",appsecret);
        param.put("sn",sn);
        param.put("type","0");
        param.put("cmd","setr");
        param.put("param","setr="+setr);
        String rs = HttpUtil.post(url,param);
        JSONObject jo  = JSONObject.parseObject(rs);
        int rsCode = jo.getInteger("code");
        if(rsCode==0){
            return R.success();
        }else{
            String msg = jo.getString("message");
            return R.error("调用接口失败："+msg+"。 错误码:"+code);
        }
    }

    public static void main(String[] args){
        String url = "https://api.netrelay.cn/api/v1/control";

        Map<String,Object> param = new HashMap<>();
        param.put("appsecret","f5a8a6c470c1404937fd2ffb00b5a6bea653aff06b313d3c76f50df492d83fbc");
        //param.put("sn","Q8a526b2e5b29757");
        param.put("sn","Q8552d92e7b77757");
        param.put("type","0");
        param.put("cmd","setr");
        param.put("param","setr=xx2xx2xx");

        String rs = HttpUtil.post(url,param);
        System.out.println(rs);
    }

}
