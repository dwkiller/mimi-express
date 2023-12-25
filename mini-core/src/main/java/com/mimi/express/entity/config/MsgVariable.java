package com.mimi.express.entity.config;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mimi.common.superpackage.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description 消息变量
 * @Author RuKai
 * @Date 2023/11/9 14:16
 **/
@Data
@TableName(value = "t_msg_variable")
public class MsgVariable implements Serializable {

    /**
     * 区域id
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 模板id
     */
    @Schema(name = "模板id")
    private String templateId;

    /**
     * 变量名
     */
    @Schema(name = "变量名")
    private String variable;

    /**
     * 变量类型
     */
    @Schema(name = "变量类型")
    private String type;

    /**
     * 变量值
     */
    @Schema(name = "变量值")
    private String value;

    /**
     * 中文
     */
    @Schema(name = "中文")
    private String tag;

}
