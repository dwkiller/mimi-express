package com.mimi.express.entity.config;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.mimi.common.superpackage.base.TenantEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 字典表
 *
 * @author lengleng
 * @date 2019/03/19
 */
@Data
@Schema(description = "字典类型")
public class SysDict extends TenantEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 类型
     */
    @Schema(description = "字典类型")
    private String type;

    /**
     * 描述
     */
    @Schema(description = "字典描述")
    private String typeName;

    /**
     * 备注信息
     */
    @Schema(description = "备注信息")
    private String remark;

    /**
     * 备注信息
     */
    @Schema(description = "系统内置")
    private Short sysDict;

}
