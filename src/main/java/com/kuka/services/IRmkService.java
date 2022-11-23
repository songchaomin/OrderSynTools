package com.kuka.services;


import com.kuka.domain.*;

import java.util.List;

public interface IRmkService {
    //客户信息同步接口
    ResultDto synCustomers(List<Customer> customers);
    //商品信息同步接口
    ResultDto synProducts(List<Product> products);
    //订单同步接口
    IOrder synOrder();
    //订单同步状态回写接口
    //status=0正常出货，status=1退货出货
    ResultDto synOrderStatus(SalOrder salOrder,int status);
    //订单状态回写接口
    ResultDto markerOrderStatus(List<String> orderNo);
    //同步库存和价格
    ResultDto synInventoryAndPrice(List<InventoryAndPrice> inventoryAndPrices);

}
