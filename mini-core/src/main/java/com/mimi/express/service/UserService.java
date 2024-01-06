package com.mimi.express.service;

import com.mimi.common.superpackage.service.ISuperService;
import com.mimi.express.entity.user.User;

public interface UserService extends ISuperService<User> {

    public User findByMobile(String mobile);

}
