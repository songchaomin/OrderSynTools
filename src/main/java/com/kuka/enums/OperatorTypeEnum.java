package com.kuka.enums;

public enum  OperatorTypeEnum {
    //类型 1：商品上传 2：客户上传 3：库存上传 4：订单下载 5：订单状态上传
    ITEM("1","商品上传"),
    CUSTOM("2","客户上传"),
    INV("3","库存上传"),
    ORDER("4","订单下载"),
    ORDERSTATUS("5","订单状态上传");

    private String type;
    private String typeName;

    OperatorTypeEnum(String type, String typeName) {
        this.typeName = typeName;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
