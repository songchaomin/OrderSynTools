package com.kuka.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InventoryAndPrice {
    private String spid;
    private String prodNo;
    private BigDecimal price;
    private int storageNumber;
}
