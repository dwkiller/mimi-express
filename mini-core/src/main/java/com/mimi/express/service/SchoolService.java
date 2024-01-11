package com.mimi.express.service;

import com.mimi.common.superpackage.service.ISuperService;
import com.mimi.express.entity.config.School;


/**
 * @author rukai
 */
public interface SchoolService extends ISuperService<School> {
    public School findByPos(double longitude,double latitude);
}
