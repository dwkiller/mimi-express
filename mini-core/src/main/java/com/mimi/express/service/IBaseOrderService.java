package com.mimi.express.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mimi.common.superpackage.service.ISuperService;
import com.mimi.express.entity.order.BaseOrder;
import com.mimi.express.entity.order.param.OrderParam;

public interface IBaseOrderService<T extends BaseOrder> extends ISuperService<T> {

    public IPage<T> findPage(OrderParam<T> param);

    public String type();

    public T findByOrderNum(String orderNum) throws Exception;
}
