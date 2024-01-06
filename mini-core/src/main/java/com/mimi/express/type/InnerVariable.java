package com.mimi.express.type;

public enum InnerVariable {

    CURRENT_TIME("系统当前时间","CURRENT_TIME","#{CURRENT_TIME}"),
    DATA_TIME("数据产生时间","DATA_TIME","#{DATA_TIME}"),
    LOGIN_EMPLOYEE("操作人","LOGIN_EMPLOYEE","#{LOGIN_EMPLOYEE}"),
    EMPLOYEE_MOBILE("操作人电话","EMPLOYEE_MOBILE","#{EMPLOYEE_MOBILE}"),
    GOODS_NUMBER("取货号","GOODS_NUMBER","#{GOODS_NUMBER}"),
    ORDER_NUMBER("运单号","ORDER_NUMBER","#{ORDER_NUMBER}"),
    EXPRESS_DELIVERY_ADDRESS("快递公司的地址","EXPRESS_DELIVERY_ADDRESS","#{EXPRESS_DELIVERY_ADDRESS}")
    ;


    private String name;
    private String code;
    private String value;


    InnerVariable(String name,String code,String value){
        this.name = name;
        this.code = code;
        this.value = value;
    }

    public String getName(){
        return name;
    }

    public String getCode(){
        return code;
    }

    public String getValue(){
        return value;
    }


}
