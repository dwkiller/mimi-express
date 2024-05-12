package com.mimi.core.express.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mimi.core.common.superpackage.service.impl.TenantServiceImpl;
import com.mimi.core.express.mapper.user.UserMapper;
import com.mimi.core.express.service.UserService;
import com.mimi.core.express.entity.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends TenantServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User findByMobile(String mobile){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getMobile,mobile);
        return super.getOne(wrapper);
    }

    @Override
    public List<User> findByMobileLast4(String mobile) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.likeRight(User::getMobile,mobile);
        return list(wrapper);
    }

    @Override
    public User findByOpenId(String openId) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getOpenId,openId);
        return baseMapper.selectOne(wrapper);
    }
}
