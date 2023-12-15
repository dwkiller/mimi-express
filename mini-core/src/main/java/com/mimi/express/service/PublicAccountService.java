package com.mimi.express.service;

import com.mimi.common.superpackage.service.ISuperService;
import com.mimi.express.entity.config.PublicAccount;


/**
 * @author rukai
 */
public interface PublicAccountService extends ISuperService<PublicAccount> {


    /**
     * 同学学校idc查询配置的公众号信息
     * @param schoolId
     * @return
     */
    public PublicAccount getBySchoolId(String schoolId);
}
