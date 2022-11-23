package com.kuka.services;

import com.kuka.domain.ResultDto;

public interface SalOrderService {
    //同步订单
    ResultDto synOrder();
    //同步订单状态
    ResultDto synOrderStatus();

    ResultDto synRebackOrderStatus();
}
