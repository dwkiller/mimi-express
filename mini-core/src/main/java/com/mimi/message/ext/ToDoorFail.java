package com.mimi.message.ext;

import com.mimi.express.entity.order.BaseOrder;
import com.mimi.message.ISendMsgExt;
import org.springframework.stereotype.Service;

/**
 * 上门收货失败通知
 */
@Service
public class ToDoorFail implements ISendMsgExt<BaseOrder> {
    @Override
    public void execute(BaseOrder order) {

    }
}
