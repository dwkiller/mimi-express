package com.mimi.system.service;

import com.mimi.core.common.superpackage.service.ISuperService;
import com.mimi.core.system.entity.Employee;
import com.mimi.core.system.service.ReadOnlyEmployeeService;

public interface EmployeeService extends ISuperService<Employee> {

    boolean updatePassword(String newPassword);

    Employee getUserInfo(String userId);

}
