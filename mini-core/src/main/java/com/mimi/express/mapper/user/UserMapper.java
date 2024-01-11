package com.mimi.express.mapper.user;

import com.mimi.common.superpackage.mapper.SuperMapper;
import com.mimi.express.entity.user.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends SuperMapper<User> {
}
