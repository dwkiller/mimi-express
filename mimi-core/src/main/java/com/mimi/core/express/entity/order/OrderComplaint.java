package com.mimi.core.express.entity.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mimi.core.common.annotation.SendMsgField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "t_order_complaint")
public class OrderComplaint extends BaseOrder implements HasExpressDelivery{

    @Schema(name = "扫描时间")
    private Date scanTime;

    @Schema(name = "是否已处理")
    private Short done;

    @Schema(name = "问题描述")
    private String descContent;

    @SendMsgField(value="#{EXPRESS_DELIVERY}",text="快递公司",translateNameBean = "expressDeliveryService")
    @Schema(name = "快递公司ID")
    private String expressDeliveryId;

    @TableField(exist = false)
    @Schema(name = "快递公司名字")
    private String expressDeliveryName;

    @Schema(name = "投诉内容")
    private String type;

    @Schema(name = "处理时间")
    private Date handTime;

    @Schema(name = "回复内容")
    private String replyContent;

    @Schema(name = "是否发送受理消息")
    private Short handMsg;

    @Schema(name = "是否发送完成消息")
    private Short doneMsg;
}
