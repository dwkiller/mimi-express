package com.mimi.express.service.impl;

import com.mimi.common.superpackage.service.impl.SuperServiceImpl;
import com.mimi.common.superpackage.service.impl.TenantServiceImpl;
import com.mimi.express.entity.config.ExpressDelivery;
import com.mimi.express.mapper.ExpressDeliveryMapper;
import com.mimi.express.service.ExpressDeliveryService;
import org.springframework.stereotype.Service;


/**
 * @Description TODO
 * @Author RuKai
 * @Date 2023/11/9 16:05
 **/
@Service
public class ExpressDeliveryServiceImpl extends TenantServiceImpl<ExpressDeliveryMapper, ExpressDelivery> implements ExpressDeliveryService {

}
