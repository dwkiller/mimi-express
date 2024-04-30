package com.mimi.express.controller.order;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mimi.core.common.R;
import com.mimi.core.common.superpackage.controller.SuperController;
import com.mimi.core.express.entity.order.BaseOrder;
import com.mimi.core.express.entity.order.param.OrderParam;
import com.mimi.core.express.service.IBaseOrderService;
import com.mimi.core.message.MessageService;
import com.mimi.express.controller.order.vo.BatchMsgBodyVo;
import com.mimi.express.controller.order.vo.BatchSendMessageVo;
import com.mimi.express.controller.order.vo.SendMessageVo;
import com.mimi.express.controller.order.vo.BatchOrderVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class BaseOrderController<S extends IBaseOrderService<T>,T extends BaseOrder> extends SuperController<S,T> {

    @Autowired
    private MessageService<T> messageService;

    @Operation(summary = "批量生成运单")
    @PostMapping("/batchSave")
    public R<?> batchSave(@RequestBody BatchOrderVo<T> batchOrderVo){
        boolean rs = superService.saveBatch(batchOrderVo.getOrderList());
        return rs?R.success():R.error("批量保存失败!");
    }

    @Operation(summary = "分页查询运单")
    @PostMapping("/findPage")
    public R<IPage<T>> findPage(@RequestBody OrderParam<T> p) throws Exception {
//        Class<T> clazzP = getParamClass();
//        OrderParam<T> p = JSONObject.toJavaObject(jsonParam,OrderParam.class);
//        T t = JSONObject.toJavaObject(jsonParam.getJSONObject("businessData"),clazzP);
//        p.setBusinessData(t);
        return R.success(superService.findPage(p));
    }

    @Operation(summary = "统计数量")
    @PostMapping("/count")
    public R<Long> count(@RequestBody OrderParam<T> p) {
        return R.success(superService.count(p));
    }

    @Operation(summary = "批量发送消息")
    @PostMapping("/batchSendMsg")
    public R batchSendMsg(@RequestBody BatchSendMessageVo batchSendMessageVo) {
        Class<T> clazzP = getParamClass();
        String errMsg = "";
        for(BatchMsgBodyVo msgVo:batchSendMessageVo.getOrderParam()){
            String orderNum = "";
            try {
                T order = JSONObject.toJavaObject(msgVo.getOrder(),clazzP);
                orderNum = order.getOrderNum();
                superService.sendMsg(batchSendMessageVo.getTemplateId(),order,msgVo.getParam(),null);
            } catch (Exception e) {
                errMsg+="运单:"+orderNum+"发送失败:"+e.getMessage()+"; ";
            }
        }
        if(errMsg.isEmpty()){
            return R.success();
        }else{
            return R.error(errMsg);
        }
    }

    @Operation(summary = "发送消息")
    @PostMapping("/sendMessage")
    public R sendMessage(@RequestBody SendMessageVo sendMessageVo) throws Exception {
        Class<T> clazzP = getParamClass();
        T order = JSONObject.toJavaObject(sendMessageVo.getOrder(),clazzP);
        superService.sendMsg(sendMessageVo.getTemplateId(),order,sendMessageVo.getParam(),sendMessageVo.getDelaySend());
        return R.success();
    }

    @Operation(summary = "根据订单号查订单")
    @GetMapping("/findByOrderNum")
    public R<T> findByOrderNum(String orderNum) throws Exception {
        return R.success(superService.findByOrderNum(orderNum));
    }

    @Operation(summary = "根据订单号开始部分查询")
    @GetMapping("/findByOrderNumStart")
    public R<List<T>> findByOrderNumStart(String orderNum) {
        return R.success(superService.findByOrderNumStart(orderNum));
    }

    private Class<T> getParamClass(){
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        Class<T> clazzP = (Class<T>) actualTypeArguments[1];
        return clazzP;
    }

}
