package com.mimi.core.express.entity.order;

import com.mimi.core.common.annotation.SendMsgField;
import com.mimi.core.common.superpackage.base.TenantEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public abstract class BaseOrder extends TenantEntity {

    @SendMsgField(value="#{ORDER_NUMBER}",text="运单号")
    @Schema(name = "运单号")
    private String orderNum;

    @SendMsgField(value="#{USER_NAME}",text="用户名")
    @Schema(name = "用户名")
    private String userName;

    @SendMsgField(value="#{USER_MOBILE}",text="用户号码")
    @Schema(name = "电话号码")
    private String mobile;
}
