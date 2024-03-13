package com.mimi.core.express.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mimi.core.common.superpackage.service.impl.TenantServiceImpl;
import com.mimi.core.express.entity.config.PayAccount;
import com.mimi.core.express.mapper.PayAccountMapper;
import com.mimi.core.express.service.PayAccountService;
import org.springframework.stereotype.Service;


/**
 * @Description TODO
 * @Author RuKai
 * @Date 2023/11/9 16:05
 **/
@Service
public class PayAccountServiceImpl extends TenantServiceImpl<PayAccountMapper, PayAccount> implements PayAccountService {

    @Override
    public PayAccount findMy() {
        Wrapper<PayAccount> wrapper = new QueryWrapper<>();
        return super.getOne(wrapper);
    }

    @Override
    public PayAccount findBySchoolId(String schoolId) {
        LambdaQueryWrapper<PayAccount> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PayAccount::getSchoolId,schoolId);
        return baseMapper.selectOne(wrapper);
    }
}
