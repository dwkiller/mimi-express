package com.mimi.core.common.superpackage.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @ClassName PageParam
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PageParam extends ListParam {
    @Schema(description = "页数")
    private Integer pageNum;

    @Schema(description = "单页行数")
    private Integer pageSize;
}
