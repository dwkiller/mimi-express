package com.mimi.core.express.service.impl.order;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mimi.core.common.superpackage.service.impl.TenantServiceImpl;
import com.mimi.core.express.service.IBaseOrderService;
import com.mimi.core.message.MessageService;
import com.mimi.core.express.entity.order.BaseOrder;
import com.mimi.core.express.entity.order.param.OrderParam;
import com.mimi.core.express.mapper.order.OrderMapper;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public abstract class BaseOrderService<M extends OrderMapper<T>, T extends BaseOrder> extends TenantServiceImpl<M,T> implements IBaseOrderService<T> {

    @Autowired
    private MessageService<T> messageService;


    @Override
    public boolean save(@Valid T t) {
        try {
            T o =findByOrderNum(t.getOrderNum());
            if(o!=null){
                throw new RuntimeException("运单号["+t.getOrderNum()+"]已经存在!");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return super.save(t);
    }

    @Override
    public void sendMsg(String templateId, T order, Map<String,String> param) throws WxErrorException {
        messageService.sendMsg(templateId,order,param);
        updateById(order);
    }

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

    @Override
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

    @Override
    public List<T> findByOrderNumStart(String orderNum){
        LambdaQueryWrapper<T> wrapper = new LambdaQueryWrapper<>();
        wrapper.likeLeft(T::getOrderNum,orderNum);
        return baseMapper.selectList(wrapper);
    }

    private Class<T> getEntityClazz(){
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        Class<T> clazzP = (Class<T>) actualTypeArguments[1];
        return clazzP;
    }


}
