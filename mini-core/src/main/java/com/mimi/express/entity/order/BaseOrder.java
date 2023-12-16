package com.mimi.express.entity.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.mimi.common.superpackage.base.TenantEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
public abstract class BaseOrder extends TenantEntity {

    @Schema(name = "订单号")
    private String orderNum;

    @Schema(name = "用户名")
    private String userName;

    @Schema(name = "电话号码")
    private String mobile;
}
