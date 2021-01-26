package com.kuka.controller;

import com.kuka.domain.ResultDto;
import com.kuka.services.SalOrderService;
import com.kuka.services.SpkfkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class SynOrderController {

    @Autowired
    private SalOrderService salOrderService;
    /**
     * 同步料品信息
     */
    @ResponseBody
    @GetMapping("order/synOrders")
    public ResultDto synItems() {
       return salOrderService.synOrder();
    }

    @ResponseBody
    @GetMapping("order/synOrderStatus")
    public ResultDto synOrderStatus() {
        return salOrderService.synOrderStatus();
    }



}
