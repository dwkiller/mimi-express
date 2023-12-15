package com.mimi.system.service;

import com.mimi.common.superpackage.service.ISuperService;
import com.mimi.system.entity.Employee;

public interface EmployeeService extends ISuperService<Employee> {

    boolean updatePassword(String newPassword);

    Employee getUserInfo(String userId);
}
