package com.mimi.core.message.ext;

import com.mimi.core.express.entity.config.MsgVariable;
import com.mimi.core.express.entity.order.BaseOrder;
import org.springframework.stereotype.Service;

/**
 * 上门收货失败通知
 */
@Service
public class ToDoorFail extends BaseMessageExt<BaseOrder> {
    @Override
    public void execute(BaseOrder order) {

    }

    @Override
    public String parameterize(BaseOrder order, MsgVariable msgVariable) {
        return null;
    }
}
