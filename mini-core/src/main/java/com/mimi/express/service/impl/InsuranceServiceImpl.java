package com.mimi.express.service.impl;

import com.mimi.common.superpackage.service.impl.SuperServiceImpl;
import com.mimi.common.superpackage.service.impl.TenantServiceImpl;
import com.mimi.express.entity.receive.Insurance;
import com.mimi.express.mapper.InsuranceMapper;
import com.mimi.express.service.InsuranceService;
import org.springframework.stereotype.Service;


/**
 * @Description TODO
 * @Author RuKai
 * @Date 2023/11/9 16:05
 **/
@Service
public class InsuranceServiceImpl extends TenantServiceImpl<InsuranceMapper, Insurance> implements InsuranceService {

}
