package com.mimi.core.express.entity.order;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "t_order_online")
public class OrderOnline extends BaseOrder{
    @Schema(name = "扫描时间")
    private Date scanTime;

    @Schema(name = "来源")
    private String source;

    @Schema(name = "寄件日期")
    private Date sendDay;

    @Schema(name = "上门取件时间")
    private Date comeTime;

    @Schema(name = "上门收件地址")
    private String comeAddress;

    @Schema(name = "是否已处理")
    private Short done;

    @Schema(name = "失败原因")
    private String failReason;

    @Schema(name = "是否已发失败消息")
    private Short failMsg;

    @Schema(name = "备注")
    private String memo;
}
