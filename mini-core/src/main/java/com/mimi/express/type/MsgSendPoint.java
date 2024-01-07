package com.mimi.express.type;

public enum MsgSendPoint {

    AGENT_HANDLE_OK("代取快递受理成功提醒","AGENT_HANDLE_OK","用户"),
    AGENT_FAIL("代取快递失败提醒","AGENT_FAIL","员工"),
    AGENT_OUT_OK("代取快递下架成功提醒","AGENT_OUT_OK","员工"),
    AGENT_GET_OK("快递代取商品派送完成通知","AGENT_GET_OK","员工"),
    AGENT_GET("快递代取商品派送通知","AGENT_GET","员工"),
    AGENT_GET_DELAY("快递代取商品延期派送通知","AGENT_GET_DELAY","员工"),
    EXPRESS_ARRIVE("快递到站通知","EXPRESS_ARRIVE","员工"),
    EXPRESS_RETENTION("滞留快递催派移库通知","EXPRESS_RETENTION","员工"),
    TO_DOOR_FAIL("上门收货失败通知","TO_DOOR_FAIL","员工"),
    COMPLAINT_ACCEPT_OK("快递投诉受理成功通知","COMPLAINT_ACCEPT_OK","用户"),
    COMPLAINT_CALLBACK("快递投诉回复通知","COMPLAINT_CALLBACK","员工"),
    ;

    private String name;
    private String code;
    private String sender;

    MsgSendPoint(String name,String code,String sender){
        this.name = name;
        this.code = code;
        this.sender = sender;
    }

    public String getName(){
        return name;
    }

    public String getCode(){
        return code;
    }

    public String getSender(){
        return sender;
    }
}
