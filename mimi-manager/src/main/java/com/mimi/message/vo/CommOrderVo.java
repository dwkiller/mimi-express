package com.mimi.message.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class CommOrderVo {

    @Schema(name = "订单号")
    private String orderNum;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+08:00")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(name = "创建时间")
    private Date createTime;

    @Schema(name = "类型")
    private String type;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+08:00")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(name = "最后移库时间")
    private Date lastMoveTime;

}
