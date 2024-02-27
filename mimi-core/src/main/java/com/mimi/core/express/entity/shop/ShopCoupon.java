package com.mimi.core.express.entity.shop;


import com.baomidou.mybatisplus.annotation.TableName;
import com.mimi.core.common.superpackage.base.TenantEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName(value = "t_shop_coupon")
public class ShopCoupon extends TenantEntity {

    @Schema(name = "名字")
    private String name;

    @Schema(name = "起始金额")
    private BigDecimal moneyThreshold;

    @Schema(name = "优惠金额")
    private BigDecimal moneyOffer;

    @Schema(name = "类型")
    private String type;

    @Schema(name = "描述")
    private String desc;

}
