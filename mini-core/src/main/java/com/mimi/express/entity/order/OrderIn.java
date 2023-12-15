package com.mimi.express.entity.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "t_order_in")
public class OrderIn extends BaseOrder implements HasExpressDelivery{

    @Schema(name = "货架号方式")
    private String rackNoType;

    @Schema(name = "货架号")
    private String rackNo;

    @Schema(name = "快递公司ID")
    private String expressDeliveryId;

    @TableField(exist = false)
    @Schema(name = "快递公司名字")
    private String expressDeliveryName;

    @Schema(name = "是否移库")
    private Short moveDb;

    @Schema(name = "移库时间")
    private Date moveDbTime;

    @Schema(name = "移库次数")
    private Integer moveDbTimes;

    @Schema(name = "是否发送消息")
    private Short sendMsg;


}
