package com.mimi.core.common.superpackage.base;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public abstract class TenantEntity extends BaseEntity{

    /**
     * 学校id
     *
     */
    @Schema(name = "学校id")
    @NotBlank(message = "学校id不能为空")
    private String schoolId;

    @Schema(name = "学校名")
    @TableField(exist = false)
    private String schoolName;
}
