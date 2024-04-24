package com.mimi.system.service;

import com.mimi.core.system.entity.Employee;
import com.mimi.core.system.service.ReadOnlyEmployeeService;

public interface EmployeeService extends ReadOnlyEmployeeService {

    boolean updatePassword(String password,String newPassword);

    boolean resetPassword();

}
