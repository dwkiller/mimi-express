package com.mimi.core.express.type;

public enum PayState {

    UN_PAY("未支付",(short)0),
    PRE_PAY("预支付",(short)1),
    PAY("已支付",(short)2);

    private String name;
    private short code;

    PayState(String name,short code){
        this.name=name;
        this.code=code;
    }

    public String getName(){
        return name;
    }

    public short getCode(){
        return code;
    }

}
