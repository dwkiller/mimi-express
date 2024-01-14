package com.mimi.system.mapper;

import com.mimi.core.common.superpackage.mapper.SuperMapper;
import com.mimi.system.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper extends SuperMapper<Employee> {

    @Select("select * from t_employee where id = #{userId}")
    Employee getEmployeeByUserId(String userId);
}
