package com.mimi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mimi.core.common.exception.MimiException;
import com.mimi.core.common.superpackage.service.impl.TenantServiceImpl;
import com.mimi.core.common.util.UserInfoUtil;
import com.mimi.core.system.entity.Employee;
import com.mimi.core.system.mapper.EmployeeMapper;
import com.mimi.core.system.service.impl.ReadonlyEmployeeServiceImpl;
import com.mimi.system.entity.LoginUser;
import com.mimi.system.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class EmployeeServiceImpl extends TenantServiceImpl<EmployeeMapper, Employee> implements EmployeeService, UserDetailsService {

    @Autowired
    private UserInfoUtil userInfoUtil;

    @Override
    public boolean save(Employee user) {
        LambdaQueryWrapper<Employee> employeeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        employeeLambdaQueryWrapper.eq(Employee::getUserName, user.getUserName());
        Employee one = this.getOne(employeeLambdaQueryWrapper);
        if (null != one) {
            throw new MimiException("该用户名已被使用：" + user.getUserName());
        }
        String password ="123456";
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode(password);
        user.setPassword(encode);
        return super.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询用户信息
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Employee::getUserName, username);
        Employee user = superMapper.selectOne(wrapper);
        //如果查询不到数据就通过抛出异常来给出提示
        if (Objects.isNull(user)) {
            throw new RuntimeException("用户名或密码错误");
        }
        //TODO 根据用户查询权限信息 添加到LoginUser中

        //封装成UserDetails对象返回
        return new LoginUser(user);
    }

    @Override
    public boolean updatePassword(String password,String newPassword) {
        String userId = userInfoUtil.getUserId();
        Employee byId = this.getById(userId);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String oldEncode = passwordEncoder.encode(password);
        if(!oldEncode.equals(byId.getPassword())){
            throw new RuntimeException("原密码输入错误!");
        }
        String encode = passwordEncoder.encode(newPassword);
        byId.setPassword(encode);
        return super.updateById(byId);
    }

    @Override
    public boolean resetPassword(String id) {
        Employee employee = this.getById(id);
        String password ="123456";
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode(password);
        employee.setPassword(encode);
        return super.updateById(employee);
    }

    @Override
    public Employee getUserInfo(String userId) {
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Employee::getId,userId);
        return getOne(wrapper);
    }
}
