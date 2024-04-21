package com.mimi.core.express.entity.order;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.mimi.core.common.annotation.SendMsgField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@TableName(value = "t_order_online")
public class OrderOnline extends BaseOrder{

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+08:00")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(name = "扫描时间")
    private Date scanTime;

    @Schema(name = "来源")
    private String source;

    @Schema(name = "寄件日期")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+08:00")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date sendDay;

    @Schema(name = "上门取件时间")
    private String comeTime;

    @Schema(name = "上门收件地址")
    private String comeAddress;

    @Schema(name = "是否已处理")
    private Short done;

    @Schema(name = "失败原因")
    @SendMsgField(value="#{FAIL_REASON}",text="失败原因")
    private String failReason;

    @Schema(name = "是否已发失败消息")
    private Short failMsg;

    @Schema(name = "备注")
    private String memo;
}
