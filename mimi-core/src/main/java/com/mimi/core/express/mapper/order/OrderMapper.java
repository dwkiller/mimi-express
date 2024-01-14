package com.mimi.core.express.mapper.order;

import com.mimi.core.common.superpackage.mapper.SuperMapper;
import com.mimi.core.express.entity.order.param.OrderParam;
import com.mimi.core.express.entity.order.BaseOrder;

import java.util.List;

public interface OrderMapper<T extends BaseOrder>  extends SuperMapper<T> {

    public static final String EXPRESS_DELIVERY_CONDITION = "<if test='businessData.expressDeliveryId != null'>"+
            " AND express_delivery_id = #{businessData.expressDeliveryId}"+
            "</if>";

    public static final String BASE_CONDITION= " WHERE 1=1 <if test='businessData.orderNum != null'>"+
            " AND order_num = #{businessData.orderNum}"+
                    "</if>"+
                    "<if test='businessData.userName != null'>"+
                    " AND user_name = #{businessData.userName}"+
                    "</if>"+
                    "<if test='businessData.mobile != null'>"+
                    " AND mobile = #{businessData.mobile}"+
                    "</if>"+
                    "<if test='startTime != null'>"+
                    " AND create_time &gt; #{startTime}"+
                    "</if>"+
                    "<if test='endTime != null'>"+
                    " AND create_time &lt; #{endTime}"+
                    "</if>"+
                    "<if test='nullMobile != null'>"+
                    " AND mobile is null"+
                    "</if>";

    public static final String DONE_CONDITION= "<if test='businessData.done != null'>"+
            " AND done = #{businessData.done}"+
            "</if>";


    public List<T> findPage(OrderParam<T> param);
    public long findCount(OrderParam<T> param);

}
