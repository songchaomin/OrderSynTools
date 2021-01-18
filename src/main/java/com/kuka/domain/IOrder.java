package com.kuka.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class IOrder {
    private  int code;
    private String msg;
    private List<SalOrder> orderList =new ArrayList<>();
}
