package com.mimi.core.common.superpackage.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Filters
 * @Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Filter {
    public static final String OP_AND = "and";
    public static final String OP_OR = "or";
    @Schema(description = "连接符")
    private String groupOp;

    @Schema(description = "规则List")
    private List<Rule> rules;

    @Schema(description = "递归子过滤器")
    private List<Filter> groups;

    public void addRule(Rule rule) {
        if (null == rule) {
            return;
        }
        if (null == rules) {
            rules = new ArrayList<>();
        }
        rules.add(rule);
    }

    public void addFilter(Filter filter) {
        if (null == filter) {
            return;
        }
        if (null == groups) {
            groups = new ArrayList<>();
        }
        groups.add(filter);
    }
}
