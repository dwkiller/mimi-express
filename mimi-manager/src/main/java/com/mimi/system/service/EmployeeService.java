package com.mimi.system.service;

import com.mimi.core.common.superpackage.service.ISuperService;
import com.mimi.core.system.entity.Employee;


public interface EmployeeService extends ISuperService<Employee> {

    Employee getUserInfo(String userId);

    boolean updatePassword(String password,String newPassword);

    boolean resetPassword(String id);

}
