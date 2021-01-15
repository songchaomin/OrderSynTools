package com.kuka.domain;

import lombok.Data;

@Data
public class Product {
    //站点id (必须)
    private String branchId;
    //商品编码 (必须)
    private String prodNo;
    //商品名称 (必须)
    private String prodName;
    //通⽤名
    private String prodLocalName;
    //商品条码
    private String prodBarcode;
    //⽣产⼚家
    private String manufacture;
    //规格 (必须)
    private String specification;
    //包装单位(必须)
    private String packageUnit;
    //是否可以拆包销售 1可以 2不可以(必须)
    private int split_package_type;
    //⼤包装单位
    private int bigPackageQuantity;
    //中包装单位(必须)
    private int midPackageQuantity;
    //批准⽂号
    private String approvalNo;
    //有效期，商品截⽌效期，近效期
    private String prodNearTime;
    //有效期，商品截⽌效期，远效期
    private String prodFarTime;
    //商品上架状态： 1 上架； 0 下架(必须)
    private int status;

}
