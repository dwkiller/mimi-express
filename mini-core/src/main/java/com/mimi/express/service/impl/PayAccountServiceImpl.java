package com.mimi.express.service.impl;

import com.mimi.common.superpackage.service.impl.SuperServiceImpl;
import com.mimi.common.superpackage.service.impl.TenantServiceImpl;
import com.mimi.express.entity.config.PayAccount;
import com.mimi.express.mapper.PayAccountMapper;
import com.mimi.express.service.PayAccountService;
import org.springframework.stereotype.Service;


/**
 * @Description TODO
 * @Author RuKai
 * @Date 2023/11/9 16:05
 **/
@Service
public class PayAccountServiceImpl extends TenantServiceImpl<PayAccountMapper, PayAccount> implements PayAccountService {

}
