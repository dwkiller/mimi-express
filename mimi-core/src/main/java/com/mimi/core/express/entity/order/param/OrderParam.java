package com.mimi.core.express.entity.order.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mimi.core.express.entity.order.BaseOrder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class OrderParam<T extends BaseOrder> {
    private int pageNum=1;
    private int pageSize=10;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+08:00")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+08:00")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    private String nullMobile;
    private String today;
    private T businessData;
    private String orderBy;
    private String desc;
    private String orderNumLike;
}
