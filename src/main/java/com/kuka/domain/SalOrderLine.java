package com.kuka.domain;

import java.math.BigDecimal;

public class SalOrderLine {
    private String outOrderCode;

    private String prodNo;

    private Integer quantity;

    private BigDecimal price;

    public String getOutOrderCode() {
        return outOrderCode;
    }

    public void setOutOrderCode(String outOrderCode) {
        this.outOrderCode = outOrderCode;
    }

    public String getProdNo() {
        return prodNo;
    }

    public void setProdNo(String prodNo) {
        this.prodNo = prodNo;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}