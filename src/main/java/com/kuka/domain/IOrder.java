package com.kuka.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


public class IOrder {
    private  int code;
    private String msg;
    private List<SalOrder> orderList =new ArrayList<>();

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<SalOrder> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<SalOrder> orderList) {
        this.orderList = orderList;
    }
}
