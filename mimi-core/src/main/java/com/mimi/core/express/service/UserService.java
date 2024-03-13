package com.mimi.core.express.service;

import com.mimi.core.common.superpackage.service.ISuperService;
import com.mimi.core.express.entity.user.User;

import java.util.List;

public interface UserService extends ISuperService<User> {

    public User findByMobile(String mobile);

    public List<User> findByMobileLast4(String mobile);

    public User findByOpenId(String openId);

}
