package com.mimi.wx.controller.security;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mimi.core.common.R;
import com.mimi.core.common.superpackage.controller.ReadOnlySuperController;
import com.mimi.core.express.entity.order.OrderOut;
import com.mimi.core.express.entity.order.param.OrderParam;
import com.mimi.core.express.service.impl.order.OrderOutService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Tag(name = "出库")
@RestController
@RequestMapping("/security/orderOut")
public class OrderOutController extends ReadOnlySuperController<OrderOutService, OrderOut> {

    @Value("${kd.outorder.fileroot}")
    private String rootPath;

    @Value("${kd.door.appsecret}")
    private String appsecret;

    @Value("${kd.door.url:https://api.netrelay.cn/api/v1/control}")
    private String url;

    @Operation(summary = "分页查询")
    @PostMapping("/findPage")
    public R<IPage<OrderOut>> findPage(@RequestBody OrderParam<OrderOut> orderParam){
        return R.success(superService.findPage(orderParam));
    }

    @Operation(summary = "批量保存出库单")
    @PostMapping("/saveBatch")
    public R<Collection<OrderOut>> saveBatch(@RequestBody Collection<OrderOut> entityList) throws Exception {
        if(superService.saveBatch(entityList,rootPath)){
            return R.success(entityList);
        }
        return R.error("保存失败!");
    }

    @Operation(summary = "批量更新出库单")
    @PostMapping("/updateBatch")
    public R updateBatch(@RequestBody List<OrderOut> orderList){
        if(orderList==null||orderList.size()==0){
            return R.error("无数据!");
        }
        String code = orderList.get(0).getGateCode();
        if(StringUtils.isEmpty(code)){
            return R.error("缺少闸口编号!");
        }
        outDoor(code);
        superService.updateBatchById(orderList);
        return R.success();
    }

    private void outDoor(String code){
        String[] codes = code.split("_");
        if(codes.length!=2){
            throw new RuntimeException("参数不合法:"+code);
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
        if(rsCode!=0){
            String msg = jo.getString("message");
            throw new RuntimeException("调用接口失败："+msg+"。 错误码:"+code);
        }
    }

}
