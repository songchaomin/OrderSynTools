package com.kuka.domain;

import lombok.Data;

import java.util.List;

@Data
public class IInventoryAndPrice {
    private String sign;
    private String timestamp;
    private String clientId;
    private String branchId;
    //客户编码,不填写则设置统⼀的价格和库存
    private String danwBh;
    private List<InventoryAndPrice> prodNoList;
}
