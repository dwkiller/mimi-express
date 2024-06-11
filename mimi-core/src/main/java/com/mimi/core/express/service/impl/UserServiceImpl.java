package com.mimi.core.express.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mimi.core.common.superpackage.service.impl.TenantServiceImpl;
import com.mimi.core.express.mapper.user.UserMapper;
import com.mimi.core.express.service.UserService;
import com.mimi.core.express.entity.user.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl extends TenantServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User findByMobileAndSchool(String mobile, String schoolId) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getMobile,mobile);
        wrapper.eq(User::getSchoolId,schoolId);
        return super.getOne(wrapper);

    }

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

    @Override
    public User findByLikeHeadBottom(String schoolId, String mobile){
        String[] mobileParts = mobile.split("\\*");
        List<String> mobilePartList = new ArrayList<>();
        for(String mobilePart:mobileParts){
            if(!StringUtils.isEmpty(mobilePart)){
                mobilePartList.add(mobilePart);
            }
        }

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        if(mobilePartList.size()==2){
            wrapper.likeRight(User::getMobile,mobilePartList.get(0));
            wrapper.likeLeft(User::getMobile,mobilePartList.get(1));
        }else if(mobilePartList.size()==1){
            wrapper.like(User::getMobile,mobilePartList.get(0));
        }else{
            return null;
        }
        wrapper.eq(User::getSchoolId,schoolId);
        List<User> userList = superMapper.selectList(wrapper);
        if(userList!=null&&userList.size()==1){
            return userList.get(0);
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getSchoolId,userInfoUtil.getSchoolId());
        return baseMapper.selectList(wrapper);
    }

}
