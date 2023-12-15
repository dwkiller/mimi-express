package com.mimi.express.entity.order.param;

import com.mimi.express.entity.order.OrderComplaint;
import lombok.Data;

@Data
public class OrderComplaintParam extends OrderComplaint implements BaseOrderParam {
    private int pageNum;
    private int pageSize;
}
