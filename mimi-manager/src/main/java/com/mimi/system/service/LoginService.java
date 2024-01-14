package com.mimi.system.service;

import com.mimi.system.entity.Employee;

import java.util.Map;

public interface LoginService {

    public Map<String,String> login(Employee user);

    public String loginOut();
}
