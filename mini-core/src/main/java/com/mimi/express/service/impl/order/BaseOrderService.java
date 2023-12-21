package com.mimi.express.service.impl.order;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mimi.common.superpackage.service.impl.TenantServiceImpl;
import com.mimi.express.entity.order.BaseOrder;
import com.mimi.express.entity.order.param.OrderParam;
import com.mimi.express.mapper.order.OrderMapper;
import com.mimi.express.service.IBaseOrderService;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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

    public T findByOrderNum(String orderNum) throws Exception {
        Class<T> clazz = getEntityClazz();
        T baseOrder = clazz.newInstance();
        baseOrder.setOrderNum(orderNum);
        baseOrder.setSchoolId(userInfoUtil.getSchoolId());
        OrderParam orderParam = new OrderParam();
        orderParam.setBusinessData(baseOrder);

        List<T> data = baseMapper.findPage(orderParam);
        if(data!=null&&data.size()>0)
        {
            return data.get(0);
        }
        return null;
    }

    private Class<T> getEntityClazz(){
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        Class<T> clazzP = (Class<T>) actualTypeArguments[1];
        return clazzP;
    }
}
