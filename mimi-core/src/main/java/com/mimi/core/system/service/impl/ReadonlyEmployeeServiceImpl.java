package com.mimi.core.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mimi.core.common.superpackage.service.impl.TenantServiceImpl;
import com.mimi.core.system.entity.Employee;
import com.mimi.core.system.mapper.EmployeeMapper;
import com.mimi.core.system.service.ReadOnlyEmployeeService;
import org.springframework.stereotype.Service;

@Service
public class ReadonlyEmployeeServiceImpl extends TenantServiceImpl<EmployeeMapper, Employee> implements ReadOnlyEmployeeService {

    @Override
    public Employee getUserInfo(String userId) {
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Employee::getId,userId);
        return getOne(wrapper);
    }

}
