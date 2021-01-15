package com.kuka.domain;

import lombok.Data;

@Data
public class Customer {
    //站点id (必须)
    private String branchId;
    //客户编码 (必须)
    private String danwBh;
    //客户名称 (必须)
    private String custName;
    //医疗执业许可证号 (必须)
    private String medicalLicenseNo;
    //收货⼈
    private String receiverName;
    //收货⼈联系电话
    private String receiverMobile;
    //收货省
    private String province;
    //收货市
    private String city;
    //收货区/县
    private String area;
    //收货详细地址
    private String detailAddress;
    //许可证到期⽇期
    private String medicalLicenseEnd;
    //营业执照到期⽇期
    private String businessLicenseEnd;
    //客户状态 1启⽤ 2禁⽤(必须)
    private int status;
}
