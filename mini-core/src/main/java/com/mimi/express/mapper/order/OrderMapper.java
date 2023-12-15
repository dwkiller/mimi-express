package com.mimi.express.mapper.order;

import com.mimi.common.superpackage.mapper.SuperMapper;
import com.mimi.express.entity.order.BaseOrder;
import com.mimi.express.entity.order.param.BaseOrderParam;

import java.util.List;

public interface OrderMapper<T extends BaseOrder,P extends BaseOrderParam>  extends SuperMapper<T> {

    public static final String BASE_CONDITION= " WHERE 1=1 <if test='orderNum != null'>"+
            " AND order_num = #{orderNum}"+
                    "</if>"+
                    "<if test='userName != null'>"+
                    " AND user_name = #{userName}"+
                    "</if>"+
                    "<if test='mobile != null'>"+
                    " AND mobile = #{mobile}"+
                    "</if>";
    public static final String DONE_CONDITION="<if test='done != null'>"+
            " AND done = #{done}"+
            "</if>";

    public static final String EXPRESS_DELIVERY_CONDITION="<if test='expressDeliveryId != null'>"+
            " AND express_delivery_id = #{expressDeliveryId}"+
            "</if>";



    public List<T> findPage(P param);
    public long findCount(P param);

}
