package com.mimi.common.enumration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 消息变量类型枚举
 */
@AllArgsConstructor
@Getter
public enum VariableTypeEnum {
    /**
     * 内置变量
     **/
    INNER(0, "INNER"),
    /**
     * 字符变量
     **/
    STRING(1, "STRING"),
    /**
     * 字典变量
     **/
    DICT(2, "DICT");
    Integer value;
    String description;
}
