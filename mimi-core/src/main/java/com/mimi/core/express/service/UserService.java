package com.mimi.core.express.service;

import com.mimi.core.common.superpackage.service.ISuperService;
import com.mimi.core.express.entity.user.User;

public interface UserService extends ISuperService<User> {

    public User findByMobile(String mobile);

}
