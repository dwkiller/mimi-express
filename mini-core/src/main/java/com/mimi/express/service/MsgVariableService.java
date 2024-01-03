package com.mimi.express.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mimi.common.superpackage.service.ISuperService;
import com.mimi.express.entity.config.MsgVariable;

import java.util.List;

/**
 * @author rukai
 */
public interface MsgVariableService extends ISuperService<MsgVariable> {

    public List<MsgVariable> findByTemplateId(String templateId);

}
