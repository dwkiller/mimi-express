package com.mimi.core.express.mapper.user;

import com.mimi.core.common.superpackage.mapper.SuperMapper;
import com.mimi.core.express.entity.user.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends SuperMapper<User> {
}
