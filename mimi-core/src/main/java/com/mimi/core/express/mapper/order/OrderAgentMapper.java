package com.mimi.core.express.mapper.order;

import com.mimi.core.express.entity.order.param.OrderParam;
import com.mimi.core.express.entity.order.OrderAgent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderAgentMapper extends OrderMapper<OrderAgent>{

    static final String CONDITION= "<if test='businessData.agentName != null'>"+
            " AND agent_name = #{businessData.agentName}"+
            "</if>"+
            "<if test='businessData.agentMobile != null'>"+
            " AND agent_mobile = #{businessData.agentMobile}"+
            "</if>"+
            "<if test='businessData.agentDoneName != null'>"+
            " AND agent_done_name = #{businessData.agentDoneName}"+
            "</if>"+
            "<if test='businessData.agentDoneMobile != null'>"+
            " AND agent_done_mobile = #{businessData.agentDoneMobile}"+
            "</if>"+
            "<if test='businessData.handMsg != null'>"+
            " AND hand_msg = #{businessData.handMsg}"+
            "</if>"+
            "<if test='businessData.okMsg != null'>"+
            " AND ok_msg = #{businessData.okMsg}"+
            "</if>"+
            "<if test='businessData.doneMsg != null'>"+
            " AND done_msg = #{businessData.doneMsg}"+
            "</if>"+
            "<if test='businessData.done != null'>"+
            " AND done = #{businessData.done}"+
            "</if>"+
            "<if test='businessData.payState != null'>"+
            " AND pay_state = #{businessData.payState}"+
            "</if>"+
            "<if test='businessData.state != null'>"+
            " AND state = #{businessData.state}"+
            "</if>"
            ;

    @Select({"<script>",
            "SELECT t.* FROM t_order_agent t",
            BASE_CONDITION,CONDITION,EXPRESS_DELIVERY_CONDITION,TIME_CONDITION,ORDER,
            "</script>"
    })
    @Override
    public List<OrderAgent> findPage(OrderParam<OrderAgent> param);

    @Select({"<script>","SELECT count(0) FROM t_order_agent",
            BASE_CONDITION,CONDITION,EXPRESS_DELIVERY_CONDITION,TIME_CONDITION,
            "</script>"})
    @Override
    public long findCount(OrderParam<OrderAgent> param);

}
