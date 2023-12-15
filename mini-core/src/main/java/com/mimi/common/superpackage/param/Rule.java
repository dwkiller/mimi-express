package com.mimi.common.superpackage.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 规则
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Rule {
    /**
     * 等于
     */
    public static final String OP_EQUAL = "eq";
    /**
     * 不等于
     */
    public static final String OP_NOT_EQUAL = "ne";
    /**
     * 小于
     */
    public static final String OP_LESS_THAN = "lt";
    /**
     * 小于等于
     */
    public static final String OP_LESS_EQUAL = "le";
    /**
     * 大于
     */
    public static final String OP_GREATER_THAN = "gt";
    /**
     * 大于等于
     */
    public static final String OP_GREATER_EQUAL = "ge";
    /**
     * 包含 ： in(xxx)
     */
    public static final String OP_IN = "in";
    /**
     * 不包含 : not in(xxx)
     */
    public static final String OP_NOT_IN = "notIn";
    /**
     * 模糊 ： like '%%'
     */
    public static final String OP_LIKE = "like";
    /**
     * not like '%xx%'
     */
    public static final String OP_NOT_LIKE = "notLike";
    /**
     * 等于空  : is null
     */
    public static final String OP_IS_NULL="isNull";
    /**
     * 不为空 ： is not null
     */
    public static final String OP_IS_NOT_NULL="isNotNull";
    /**
     * 左like  : like 'X%'
     */
    public static final String OP_LEFT_LIKE = "leftLike";
    /**
     *右like : like '%X'
     */
    public static final String OP_RIGHT_LIKE = "rightLike";
    @Schema(description = "字段")
    private String field;

    @Schema(description = "连接符")
    private String op;

    @Schema(description = "数据")
    private Object data;

    @Schema(description = "拼接条件")
    private Boolean condition;

    public Rule(String field, String op, Object data) {
        this.field = field;
        this.op = op;
        this.data = data;
    }
}

