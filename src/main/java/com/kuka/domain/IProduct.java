package com.kuka.domain;

import lombok.Data;

import java.util.List;

@Data
public class IProduct {
    private String sign;
    private String timestamp;
    private String clientId;
    private List<Product> merchandiseList;
}
