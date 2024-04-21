package com.mimi.core.system.service;

import com.mimi.core.common.superpackage.service.ISuperService;
import com.mimi.core.system.entity.Employee;

public interface ReadOnlyEmployeeService extends ISuperService<Employee> {

    Employee getUserInfo(String userId);
}
