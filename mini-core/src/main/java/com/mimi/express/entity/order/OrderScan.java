package com.mimi.express.entity.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@TableName(value = "t_order_scan")
public class OrderScan extends BaseOrder implements HasExpressDelivery{

    @Schema(name = "快递公司ID")
    private String expressDeliveryId;

    @TableField(exist = false)
    @Schema(name = "快递公司名字")
    private String expressDeliveryName;
}
