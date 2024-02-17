package com.mimi.core.express.service;


import com.mimi.core.common.superpackage.service.ISuperService;
import com.mimi.core.express.entity.config.MsgVariable;

import java.util.List;

/**
 * @author rukai
 */
public interface MsgVariableService extends ISuperService<MsgVariable> {

    public List<MsgVariable> findByTemplateId(String templateId);

    public void deleteByTemplateId(String templateId);
}
