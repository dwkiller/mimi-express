package com.mimi.core.express.entity.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mimi.core.common.annotation.SendMsgField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@TableName(value = "t_order_scan")
public class OrderScan extends BaseOrder implements HasExpressDelivery{

    @Schema(name = "快递公司ID")
    private String expressDeliveryId;

    @SendMsgField(value="#{EXPRESS_DELIVERY}",text="快递公司")
    @TableField(exist = false)
    @Schema(name = "快递公司名字")
    private String expressDeliveryName;
}
