package com.mimi.core.express.service.impl;

import com.mimi.core.common.superpackage.service.impl.TenantServiceImpl;
import com.mimi.core.express.entity.receive.Insurance;
import com.mimi.core.express.mapper.InsuranceMapper;
import com.mimi.core.express.service.InsuranceService;
import org.springframework.stereotype.Service;


/**
 * @Description TODO
 * @Author RuKai
 * @Date 2023/11/9 16:05
 **/
@Service
public class InsuranceServiceImpl extends TenantServiceImpl<InsuranceMapper, Insurance> implements InsuranceService {

}
