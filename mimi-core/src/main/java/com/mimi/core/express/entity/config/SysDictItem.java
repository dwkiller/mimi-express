package com.mimi.core.express.entity.config;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 字典项
 *
 * @author lengleng
 * @date 2019/03/19
 */
@Data
@Schema(description = "字典项")
public class SysDictItem{

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "字典项id")
    private String id;

    /**
     * 标签名
     */
    @Schema(description = "标签名")
    private String label;

    /**
     * 描述
     */
    @Schema(description = "描述")
    private String description;

    /**
     * 备注信息
     */
    @Schema(description = "备注信息")
    private String remark;


    /**
     * 所属字典类id
     */
    @Schema(description = "所属字典类Id")
    private String dictId;

    /**
     * 所属字典类id
     */
    @TableField(exist = false)
    @Schema(description = "所属字典类TypeName")
    private String typeName;

    @TableField(exist = false)
    private String type;

}
