package com.mimi.core.express.service;

import com.mimi.core.common.superpackage.service.ISuperService;
import com.mimi.core.express.entity.config.School;


/**
 * @author rukai
 */
public interface SchoolService extends ISuperService<School> {
    public School findByPos(double longitude,double latitude);
}
