package com.mimi.core.express.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mimi.core.common.exception.MimiException;
import com.mimi.core.common.superpackage.service.impl.TenantServiceImpl;
import com.mimi.core.express.entity.config.NoticeTemp;
import com.mimi.core.express.entity.config.PublicAccount;
import com.mimi.core.express.entity.config.School;
import com.mimi.core.express.mapper.NoticeTempMapper;
import com.mimi.core.express.service.NoticeTempService;
import com.mimi.core.express.service.SchoolService;
import com.mimi.core.express.service.PublicAccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @Description TODO
 * @Author RuKai
 * @Date 2023/11/9 16:05
 **/
@Service
public class NoticeTempServiceImpl extends TenantServiceImpl<NoticeTempMapper, NoticeTemp> implements NoticeTempService {

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private PublicAccountService publicAccountService;

    @Override
    @Transactional
    public boolean save(NoticeTemp noticeTemp){
        School school = schoolService.getById(noticeTemp.getSchoolId());
        if (null == school){
            throw new MimiException("未找到对应学校： " + noticeTemp.getSchoolId());
        }
        LambdaQueryWrapper<PublicAccount> publicAccountLambdaQueryWrapper = new LambdaQueryWrapper<>();
        publicAccountLambdaQueryWrapper.eq(PublicAccount::getSchoolId,school.getId());
        List<PublicAccount> list = publicAccountService.list(publicAccountLambdaQueryWrapper);
        if (CollUtil.isEmpty(list)){
            throw new MimiException("该学校未绑定公众号，无法保存消息配置");
        }
        return super.save(noticeTemp);
    }

    @Override
    public NoticeTemp findByTemplateId(String templateId) {
        LambdaQueryWrapper<NoticeTemp> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(NoticeTemp::getTemplateId,templateId);
        return getOne(wrapper);
    }

    @Override
    public NoticeTemp findByPoint(String point) {
        LambdaQueryWrapper<NoticeTemp> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(NoticeTemp::getSendPoint,point);
        return getOne(wrapper);
    }
}
