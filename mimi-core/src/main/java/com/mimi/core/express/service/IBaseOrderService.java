package com.mimi.core.express.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mimi.core.common.superpackage.service.ISuperService;
import com.mimi.core.express.entity.order.BaseOrder;
import com.mimi.core.express.entity.order.param.OrderParam;

import java.util.List;
import java.util.Map;

public interface IBaseOrderService<T extends BaseOrder> extends ISuperService<T> {

    public void sendMsg(String templateId, T order, Map<String,String> param,Integer delaySend) throws Exception;

    public IPage<T> findPage(OrderParam<T> param);

    public long count(OrderParam<T> param);

    public String type();

    public T findByOrderNum(String orderNum) throws Exception;

    public List<T> findMultipleByOrderNum(String orderNum) throws Exception;

    public List<T> findByOrderNumStart(String orderNum);

    public List<String> existsOrderNum(String schoolId,List<String> orderNumList);
}
