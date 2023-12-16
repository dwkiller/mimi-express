package com.mimi.express.service.impl.order;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mimi.common.superpackage.service.impl.TenantServiceImpl;
import com.mimi.express.entity.order.BaseOrder;
import com.mimi.express.entity.order.param.OrderParam;
import com.mimi.express.mapper.order.OrderMapper;
import com.mimi.express.service.IBaseOrderService;

import java.util.List;

public abstract class BaseOrderService<M extends OrderMapper<T>, T extends BaseOrder> extends TenantServiceImpl<M,T> implements IBaseOrderService<T> {



    @Override
    public IPage<T> findPage(OrderParam<T> param){
        param.getBusinessData().setSchoolId(userInfoUtil.getSchoolId());
        List<T> data = baseMapper.findPage(param);
        long cnt = baseMapper.findCount(param);
        long pages = 0;
        if(data!=null){
            pages = data.size()%param.getPageSize()==0?data.size()/param.getPageSize()+1:data.size()/param.getPageSize();
        }
        IPage<T> result = new Page<>();
        result.setPages(pages);
        result.setRecords(data);
        result.setTotal(cnt);
        return result;
    }
}
