package com.mimi.express.service.impl;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mimi.common.exception.MimiException;
import com.mimi.common.superpackage.service.impl.SuperServiceImpl;
import com.mimi.common.superpackage.service.impl.TenantServiceImpl;
import com.mimi.express.entity.config.PublicAccount;
import com.mimi.express.mapper.PublicAccountMapper;
import com.mimi.express.service.PublicAccountService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;


/**
 * @Description TODO
 * @Author RuKai
 * @Date 2023/11/9 16:05
 **/
@Service
public class PublicAccountServiceImpl extends TenantServiceImpl<PublicAccountMapper, PublicAccount> implements PublicAccountService {

    @Value("kd.public.fileroot")
    private String fileRoot;

    @Override
    @Transactional
    public boolean save(PublicAccount publicAccount) {
        if (countByAppId(publicAccount.getAppId(),publicAccount.getSchoolId())>0) {
            throw new MimiException("该公众号已经被使用！");
        }
        FileUtil.mkdir(fileRoot+ File.separator+publicAccount.getAppId());
        return super.save(publicAccount);
    }

    @Override
    @Transactional
    public boolean updateById(PublicAccount publicAccount) {
        if (countByAppId(publicAccount.getAppId(),publicAccount.getSchoolId())>0) {
            throw new MimiException("该公众号已经被使用！");
        }
        return super.updateById(publicAccount);
    }

    @Override
    public PublicAccount getBySchoolId(String schoolId) {
        LambdaQueryWrapper<PublicAccount> publicAccountLambdaQueryWrapper = new LambdaQueryWrapper<>();
        publicAccountLambdaQueryWrapper.eq(PublicAccount::getSchoolId, schoolId);
        return this.getOne(publicAccountLambdaQueryWrapper);
    }

    public int countByAppId(String appId,String schoolId){
        LambdaQueryWrapper<PublicAccount> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PublicAccount::getAppId,appId);
        wrapper.ne(PublicAccount::getSchoolId,schoolId);
        return baseMapper.selectCount(wrapper);
    }

}
