package com.mimi.express.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mimi.common.exception.MimiException;
import com.mimi.common.superpackage.service.impl.SuperServiceImpl;
import com.mimi.common.superpackage.service.impl.TenantServiceImpl;
import com.mimi.express.entity.config.NoticeTemp;
import com.mimi.express.entity.config.PublicAccount;
import com.mimi.express.entity.config.School;
import com.mimi.express.mapper.NoticeTempMapper;
import com.mimi.express.service.NoticeTempService;
import com.mimi.express.service.PublicAccountService;
import com.mimi.express.service.SchoolService;
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
        return baseMapper.selectOne(wrapper);
    }
}
