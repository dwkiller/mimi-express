package com.mimi.express.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mimi.common.superpackage.service.ISuperService;
import com.mimi.express.entity.order.BaseOrder;
import com.mimi.express.entity.order.param.BaseOrderParam;
import com.mimi.express.mapper.order.OrderMapper;

public interface IBaseOrderService<T extends BaseOrder
        ,P extends BaseOrderParam> extends ISuperService<T> {

    public IPage<T> findPage(P param);
}
