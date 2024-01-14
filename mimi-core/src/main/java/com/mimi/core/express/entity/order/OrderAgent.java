package com.mimi.core.express.entity.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName(value = "t_order_agent")
public class OrderAgent extends BaseOrder implements HasExpressDelivery{

    @Schema(name = "是否已处理")
    private Short done;

    @Schema(name = "备注")
    private String memo;

    @Schema(name = "快递公司ID")
    private String expressDeliveryId;

    @TableField(exist = false)
    @Schema(name = "快递公司名字")
    private String expressDeliveryName;

    @Schema(name = "投诉内容")
    private String type;

    @Schema(name = "扫描时间")
    private Date scanTime;

    @Schema(name = "处理时间")
    private Date handTime;

    @Schema(name = "回复内容")
    private String replyContent;

    @Schema(name = "是否发送受理消息")
    private Short handMsg;

    @Schema(name = "是否发送完成消息")
    private Short doneMsg;

    @Schema(name = "货架号")
    private String rackNo;

    @Schema(name = "金额")
    private BigDecimal money;

    @Schema(name = "支付金额")
    private BigDecimal payMoney;

    @Schema(name = "优惠金额")
    private BigDecimal preferentialMoney;

    @Schema(name = "公寓名字")
    private String buildName;

    @Schema(name = "寝室号")
    private String roomName;

    @Schema(name = "取货时间")
    private Date pickUpTime;

    @Schema(name = "代取人名称")
    private String agentName;

    @Schema(name = "代取人电话")
    private String agentMobile;

    @Schema(name = "代取完成时间")
    private Date agentTime;

    @Schema(name = "派送费")
    private BigDecimal givingMoney;

    @Schema(name = "是否发送成功消息")
    private Short okMsg;

    @Schema(name = "代取完成人名称")
    private String agentDoneName;

    @Schema(name = "代取完成人电话")
    private String agentDoneMobile;
}
