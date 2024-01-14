package com.mimi.core.express.entity.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mimi.core.common.superpackage.base.TenantEntity;
import lombok.Data;

@Data
@TableName(value = "t_user")
public class User extends TenantEntity {
    private String userName;
    private String mobile;
    private String openId;

    @TableField(exist = false)
    private String authCode;
}
