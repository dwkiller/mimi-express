package com.mimi.common.superpackage.param;

import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.LinkedHashMap;

/**
 * @ClassName PageParam
 * @Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ListParam {

    public final static String DESC = SqlKeyword.DESC.getSqlSegment();
    public final static String ASC = SqlKeyword.ASC.getSqlSegment();

    @Schema(description = "过滤器")
    private Filter filter;

    @Schema(description = "排序器")
    private LinkedHashMap<String, String> sorts;
}
