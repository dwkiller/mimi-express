package com.mimi.express.entity.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName(value = "t_order_question")
public class OrderQuestion extends BaseOrder implements HasExpressDelivery{

    @Schema(name = "扫描时间")
    private Date scanTime;

    @Schema(name = "剩余时间")
    private Date leaveTime;

    @Schema(name = "是否已处理")
    private Short done;

    @Schema(name = "问题描述")
    private String descContent;

    @Schema(name = "快递公司ID")
    private String expressDeliveryId;

    @TableField(exist = false)
    @Schema(name = "快递公司名字")
    private String expressDeliveryName;

    @Schema(name = "损失金额")
    private BigDecimal lossMoney;
}
