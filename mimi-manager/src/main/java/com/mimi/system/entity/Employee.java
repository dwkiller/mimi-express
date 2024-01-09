package com.mimi.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mimi.common.superpackage.base.BaseEntity;
import com.mimi.common.superpackage.base.TenantEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 员工实体
 */
@Data
@TableName(value = "t_employee")
public class Employee extends TenantEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(name = "姓名")
    @NotBlank(message = "姓名不能为空")
    private String realName;

    @Schema(name = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String userName;

    @Schema(name = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @Schema(name = "手机号码")
    private String phone;

    @Schema(name = "是否是学校管理员")
    private boolean forAdmin;

    @Schema(name = "公众号openId")
    private String openId;

    @TableField(exist = false)
    @Schema(name = "authCode")
    private String authCode;

}
