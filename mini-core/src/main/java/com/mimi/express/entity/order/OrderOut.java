package com.mimi.express.entity.order;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "t_order_out")
public class OrderOut extends BaseOrder{

    @Schema(name = "扫描时间")
    private Date scanTime;

    @Schema(name = "是否发送消息")
    private Short sendMsg;

    @Schema(name = "闸门编码")
    private String gateCode;

    @Schema(name = "来源")
    private String source;
}
