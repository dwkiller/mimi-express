package com.mimi.core.express.service;

import com.mimi.core.common.superpackage.service.ISuperService;
import com.mimi.core.express.entity.config.PayAccount;

/**
 * @author rukai
 */
public interface PayAccountService extends ISuperService<PayAccount> {

    public PayAccount findMy();

    public PayAccount findBySchoolId(String schoolId);
}
