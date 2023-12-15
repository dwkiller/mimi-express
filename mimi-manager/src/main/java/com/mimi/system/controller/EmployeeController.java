package com.mimi.system.controller;

import com.mimi.common.R;
import com.mimi.common.superpackage.controller.SuperController;
import com.mimi.system.entity.Employee;
import com.mimi.system.service.EmployeeService;
import com.mimi.system.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "用户管理")
@RestController
@RequestMapping("/employee")
public class EmployeeController extends SuperController<EmployeeService, Employee> {

    @Autowired
    private LoginService loginService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    @Operation(summary = "用户登录接口")
    public R login(@RequestBody Employee user){
        return R.success(loginService.login(user));
    }

    @GetMapping("/logout")
    @Operation(summary = "用户登出接口")
    public R logout(){
        return R.success(loginService.loginOut());
    }


    @GetMapping("/updatePassword")
    @Operation(summary = "修改密码")
    public R updatePassword(@RequestParam(name = "newPassword")String newPassword){
        return R.success(employeeService.updatePassword(newPassword));
    }

    @GetMapping("/getUserInfo")
    @Operation(summary = "根据用户id获取用户信息")
    public R getUserInfo(@RequestParam(name = "userId")String userId){
        return R.success(employeeService.getUserInfo(userId));
    }
}
