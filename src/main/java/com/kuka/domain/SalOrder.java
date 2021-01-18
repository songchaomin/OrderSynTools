package com.kuka.domain;

import java.util.ArrayList;
import java.util.List;

public class SalOrder {
    private String outOrderCode;

    private String branchId;

    private String danwbh;

    private String createTime;

    private String note;

    private Byte isOnlinePay;

    private List<SalOrderLine> orderDetail=new ArrayList<>();


    public List<SalOrderLine> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<SalOrderLine> orderDetail) {
        this.orderDetail = orderDetail;
    }

    public String getOutOrderCode() {
        return outOrderCode;
    }

    public void setOutOrderCode(String outOrderCode) {
        this.outOrderCode = outOrderCode;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getDanwbh() {
        return danwbh;
    }

    public void setDanwbh(String danwbh) {
        this.danwbh = danwbh;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Byte getIsOnlinePay() {
        return isOnlinePay;
    }

    public void setIsOnlinePay(Byte isOnlinePay) {
        this.isOnlinePay = isOnlinePay;
    }
}