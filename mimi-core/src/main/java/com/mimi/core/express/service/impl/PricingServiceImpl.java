package com.mimi.core.express.service.impl;

import com.mimi.core.common.superpackage.service.impl.TenantServiceImpl;
import com.mimi.core.express.entity.receive.Pricing;
import com.mimi.core.express.mapper.PricingMapper;
import com.mimi.core.express.service.PricingService;
import org.springframework.stereotype.Service;


/**
 * @Description TODO
 * @Author RuKai
 * @Date 2023/11/9 16:05
 **/
@Service
public class PricingServiceImpl extends TenantServiceImpl<PricingMapper, Pricing> implements PricingService {

}
