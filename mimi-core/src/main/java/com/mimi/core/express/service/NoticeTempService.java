package com.mimi.core.express.service;

import com.mimi.core.common.superpackage.service.ISuperService;
import com.mimi.core.express.entity.config.NoticeTemp;


/**
 * @author rukai
 */
public interface NoticeTempService extends ISuperService<NoticeTemp> {

    public NoticeTemp findByTemplateId(String templateId);
}
