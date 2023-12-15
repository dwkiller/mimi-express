package com.mimi.express.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mimi.common.superpackage.service.impl.SuperServiceImpl;
import com.mimi.express.entity.config.MsgVariable;
import com.mimi.express.mapper.MsgVariableMapper;
import com.mimi.express.service.MsgVariableService;
import org.springframework.stereotype.Service;


/**
 * @Description TODO
 * @Author RuKai
 * @Date 2023/11/9 16:05
 **/
@Service
public class MsgVariableServiceImpl extends SuperServiceImpl<MsgVariableMapper,MsgVariable> implements MsgVariableService
{

}
