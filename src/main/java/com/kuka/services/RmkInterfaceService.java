package com.kuka.services;


import com.kuka.domain.Customer;
import com.kuka.domain.ResultDto;

import java.util.List;

public interface RmkInterfaceService {
    //客户信息同步接口
    ResultDto synCustomers(List<Customer> customers);
    //商品信息同步接口
    ResultDto synItems(List<String> items);
    //订单同步接口
    List<String> synOrder();
    //订单同步状态回写接口
    ResultDto synOrderMark(List<String> orderNo);
    //订单状态回写接口
    ResultDto synOrderStatus (List<String> orderNo);
}
