package com.mimi.express.controller.order.vo;

import com.mimi.core.express.entity.order.BaseOrder;
import lombok.Data;

import java.util.List;

@Data
public class BatchOrderVo<T extends BaseOrder> {

    private List<T> orderList;
}
