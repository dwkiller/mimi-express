package com.mimi.express.controller.order;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mimi.common.R;
import com.mimi.common.superpackage.controller.SuperController;
import com.mimi.express.entity.order.BaseOrder;
import com.mimi.express.entity.order.param.BaseOrderParam;
import com.mimi.express.service.IBaseOrderService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class BaseOrderController<S extends IBaseOrderService<T,P>,T extends BaseOrder,P extends BaseOrderParam> extends SuperController<S,T> {

    @Operation(summary = "分页查询运单")
    @PostMapping("/findPage")
    public R<IPage<T>> findPage(@RequestBody JSONObject jsonParam) throws Exception {
        Class<P> clazzP = getParamClass();
        P p = JSONObject.toJavaObject(jsonParam,clazzP);
        return R.success(superService.findPage(p));
    }

    private Class<P> getParamClass(){
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        Class<P> clazzP = (Class<P>) actualTypeArguments[2];
        return clazzP;
    }

}
