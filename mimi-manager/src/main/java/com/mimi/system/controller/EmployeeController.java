package com.mimi.system.controller;

import com.mimi.core.common.R;
import com.mimi.core.common.superpackage.controller.SuperController;
import com.mimi.core.system.entity.Employee;
import com.mimi.core.system.service.ReadOnlyEmployeeService;
import com.mimi.system.service.LoginService;
import com.mimi.system.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@Tag(name = "用户管理")
@RestController
@RequestMapping("/employee")
public class EmployeeController extends SuperController<ReadOnlyEmployeeService, Employee> {

    @Autowired
    private LoginService loginService;

    @Autowired
    private EmployeeService employeeService;


    @PostMapping("/login")
    @Operation(summary = "用户登录接口")
    public R login(@RequestBody Employee user){
        Map<String,String> rs = loginService.login(user);
        return R.success(rs);
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
