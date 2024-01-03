package com.mimi.express.entity.user;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mimi.common.superpackage.base.BaseEntity;
import com.mimi.common.superpackage.base.TenantEntity;
import lombok.Data;

@Data
@TableName(value = "t_user")
public class User extends TenantEntity {
    private String userName;
    private String password;
    private String mobile;
    private String appOpenId;
    private String gzhOpenId;
}
