package com.mimi.express.controller;


import com.mimi.core.common.R;
import com.mimi.core.common.superpackage.controller.SuperController;
import com.mimi.core.express.entity.config.School;
import com.mimi.core.express.service.SchoolService;
import com.mimi.core.system.entity.Employee;
import com.mimi.core.system.service.ReadOnlyEmployeeService;
import com.mimi.system.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


/**
 * <p>
 * 学校信息管理
 * </p>
 *
 * @author 茹凯
 * @since 2023/10/31
 */
@Slf4j
@Tag(name = "学校管理")
@RestController
@RequestMapping("/school")
public class SchoolController extends SuperController<SchoolService, School> {

    @Autowired
    private EmployeeService employeeService;

    @Override
    @Operation(summary = "新增实体")
    @PostMapping("")
    @Transactional
    public R<Boolean> save(@RequestBody @Valid School school) {
        superService.save(school);
        Employee employee = new Employee();
        employee.setUserName(school.getAdminName());
        employee.setPassword(school.getAdminPassword());
        employee.setForAdmin(true);
        employee.setSchoolId(school.getId());
        return R.success(employeeService.save(employee));
    }

}
