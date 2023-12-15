package com.mimi.express.service.impl;

import com.mimi.common.superpackage.service.impl.SuperServiceImpl;
import com.mimi.common.superpackage.service.impl.TenantServiceImpl;
import com.mimi.express.entity.receive.Pricing;
import com.mimi.express.mapper.PricingMapper;
import com.mimi.express.service.PricingService;
import org.springframework.stereotype.Service;


/**
 * @Description TODO
 * @Author RuKai
 * @Date 2023/11/9 16:05
 **/
@Service
public class PricingServiceImpl extends TenantServiceImpl<PricingMapper, Pricing> implements PricingService {

}
