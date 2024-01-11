package com.mimi.express.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mimi.common.superpackage.service.impl.TenantServiceImpl;
import com.mimi.express.entity.user.User;
import com.mimi.express.mapper.user.UserMapper;
import com.mimi.express.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends TenantServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User findByMobile(String mobile){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getMobile,mobile);
        return baseMapper.selectOne(wrapper);
    }
}
