package com.mimi.core.express.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.mimi.core.common.superpackage.service.impl.SuperServiceImpl;
import com.mimi.core.express.entity.config.MsgVariable;
import com.mimi.core.express.mapper.MsgVariableMapper;
import com.mimi.core.express.service.MsgVariableService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;


/**
 * @Description TODO
 * @Author RuKai
 * @Date 2023/11/9 16:05
 **/
@Service
public class MsgVariableServiceImpl extends SuperServiceImpl<MsgVariableMapper,MsgVariable> implements MsgVariableService
{

    @Override
    public List<MsgVariable> findByTemplateId(String templateId) {
        LambdaQueryWrapper<MsgVariable> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MsgVariable::getTemplateId,templateId);
        return list(wrapper);
    }

    @Override
    public void deleteByTemplateId(String templateId) {
        LambdaUpdateWrapper<MsgVariable> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(MsgVariable::getTemplateId,templateId);
        super.remove(wrapper);
    }


}
