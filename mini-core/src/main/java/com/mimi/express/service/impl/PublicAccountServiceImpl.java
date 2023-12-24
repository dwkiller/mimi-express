package com.mimi.express.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mimi.common.exception.MimiException;
import com.mimi.common.superpackage.service.impl.SuperServiceImpl;
import com.mimi.common.superpackage.service.impl.TenantServiceImpl;
import com.mimi.express.entity.config.PublicAccount;
import com.mimi.express.mapper.PublicAccountMapper;
import com.mimi.express.service.PublicAccountService;
import org.springframework.stereotype.Service;


/**
 * @Description TODO
 * @Author RuKai
 * @Date 2023/11/9 16:05
 **/
@Service
public class PublicAccountServiceImpl extends TenantServiceImpl<PublicAccountMapper, PublicAccount> implements PublicAccountService {

    @Override
    public boolean save(PublicAccount publicAccount) {
        PublicAccount one = this.getBySchoolId(publicAccount.getSchoolId());
        if (null != one) {
            throw new MimiException("该学校已绑定公众号配置！");
        }
        return super.save(publicAccount);
    }

    @Override
    public boolean updateById(PublicAccount publicAccount) {
        PublicAccount one = this.getBySchoolId(publicAccount.getSchoolId());
        if (null != one) {
            throw new MimiException("该学校已绑定公众号配置！");
        }
        return super.updateById(publicAccount);
    }

    public PublicAccount getBySchoolId(String schoolId) {
        LambdaQueryWrapper<PublicAccount> publicAccountLambdaQueryWrapper = new LambdaQueryWrapper<>();
        publicAccountLambdaQueryWrapper.eq(PublicAccount::getSchoolId, schoolId);
        return this.getOne(publicAccountLambdaQueryWrapper);
    }
}
