package com.kuka.domain;

import java.util.ArrayList;
import java.util.List;

public class SalOrder {
    private String outOrderCode;

    private String branchId;

    private String danwBh;

    private String createTime;

    private String note;

    private Byte isOnlinePay;

    private String isZx;

    private Byte uploadStatus;

    //时间(必须)
    private String timestamp;

    //⽤于区分应⽤(必须)
    private String clientId;

    //贵⽅订单编号(必须)
    private String orderCode;

    //订单状态 11:待⽀付,0:待出库,5:已出库,6:已签收,8:提交失败,12:已取消(⾃动取消,3：全部冲红,17： ERP删除) (必须)
    private int orderStatus;

    //⽀付流⽔
    private String payTxCode;

    //⽀付⾦额(必须)
    private int payAmount;

    //⽀付状态 1-⽀付成功 0-未⽀付(必须)
    private String payStatus;

    //提交失败原因(必须)
    private String failMsg;

    private List<SalOrderLine> orderDetail=new ArrayList<>();

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPayTxCode() {
        return payTxCode;
    }

    public void setPayTxCode(String payTxCode) {
        this.payTxCode = payTxCode;
    }

    public int getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(int payAmount) {
        this.payAmount = payAmount;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getFailMsg() {
        return failMsg;
    }

    public void setFailMsg(String failMsg) {
        this.failMsg = failMsg;
    }

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

    public String getDanwBh() {
        return danwBh;
    }

    public void setDanwBh(String danwBh) {
        this.danwBh = danwBh;
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

    public String getIsZx() {
        return isZx;
    }

    public void setIsZx(String isZx) {
        this.isZx = isZx;
    }

    public Byte getUploadStatus() {
        return uploadStatus;
    }

    public void setUploadStatus(Byte uploadStatus) {
        this.uploadStatus = uploadStatus;
    }
}