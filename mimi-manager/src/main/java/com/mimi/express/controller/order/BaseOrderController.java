package com.mimi.express.controller.order;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mimi.core.common.R;
import com.mimi.core.common.superpackage.controller.SuperController;
import com.mimi.core.express.entity.order.BaseOrder;
import com.mimi.core.express.entity.order.param.OrderParam;
import com.mimi.core.express.service.IBaseOrderService;
import com.mimi.core.message.MessageService;
import com.mimi.core.message.vo.SendMessageVo;
import io.swagger.v3.oas.annotations.Operation;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class BaseOrderController<S extends IBaseOrderService<T>,T extends BaseOrder> extends SuperController<S,T> {

    @Autowired
    private MessageService<T> messageService;

    @Operation(summary = "分页查询运单")
    @PostMapping("/findPage")
    public R<IPage<T>> findPage(@RequestBody JSONObject jsonParam) throws Exception {
        Class<T> clazzP = getParamClass();
        OrderParam<T> p = JSONObject.toJavaObject(jsonParam,OrderParam.class);
        T t = JSONObject.toJavaObject(jsonParam.getJSONObject("businessData"),clazzP);
        p.setBusinessData(t);
        return R.success(superService.findPage(p));
    }

    @Operation(summary = "发送消息")
    @PostMapping("/sendMessage")
    public R sendMessage(@RequestBody SendMessageVo sendMessageVo) throws WxErrorException {
        Class<T> clazzP = getParamClass();
        T order = JSONObject.toJavaObject(sendMessageVo.getOrder(),clazzP);
        superService.sendMsg(sendMessageVo.getTemplateId(),order,sendMessageVo.getParam());
        return R.success();
    }

    private Class<T> getParamClass(){
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        Class<T> clazzP = (Class<T>) actualTypeArguments[1];
        return clazzP;
    }

}
