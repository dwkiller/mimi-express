package com.mimi.express.service.impl.order;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mimi.common.superpackage.service.impl.TenantServiceImpl;
import com.mimi.express.entity.config.ExpressDelivery;
import com.mimi.express.entity.config.MsgVariable;
import com.mimi.express.entity.config.NoticeTemp;
import com.mimi.express.entity.config.PublicAccount;
import com.mimi.express.entity.order.BaseOrder;
import com.mimi.express.entity.order.HasExpressDelivery;
import com.mimi.express.entity.order.param.OrderParam;
import com.mimi.express.entity.user.User;
import com.mimi.express.mapper.order.OrderMapper;
import com.mimi.express.service.*;
import com.mimi.express.type.InnerVariable;
import com.mimi.message.WxMessageParameterize;
import com.mimi.wx.WxService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class BaseOrderService<M extends OrderMapper<T>, T extends BaseOrder> extends TenantServiceImpl<M,T> implements IBaseOrderService<T> {

    @Autowired
    private ExpressDeliveryService expressDeliveryService;

    protected String getExpressAddress(T order){
        if(order instanceof HasExpressDelivery){
            HasExpressDelivery hasExpressDelivery = (HasExpressDelivery)order;
            ExpressDelivery expressDelivery = expressDeliveryService.getById(
                    hasExpressDelivery.getExpressDeliveryId());
            if(expressDelivery==null){
                return "";
            }
            return expressDelivery.getName();
        }
        return "";
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

    private Class<T> getEntityClazz(){
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        Class<T> clazzP = (Class<T>) actualTypeArguments[1];
        return clazzP;
    }


}
