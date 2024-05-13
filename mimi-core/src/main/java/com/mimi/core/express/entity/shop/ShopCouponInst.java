package com.mimi.core.express.entity.shop;


import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "t_shop_coupon_inst")
public class ShopCouponInst extends ShopCoupon{

    @Schema(name = "优惠卷ID")
    private String shopCouponId;

    @Schema(name = "用户ID")
    private String userId;

    @Schema(name = "到期时间")
    private Date endTime;

    @Schema(name = "运单号")
    private String orderNum;

    @Schema(name = "使用状态")
    private short state;
}
