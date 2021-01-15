package com.kuka.services.impl;

import com.alibaba.fastjson.JSONObject;
import com.kuka.dao.SpkfkExtMapper;
import com.kuka.dao.SpkfkMapper;
import com.kuka.domain.Customer;
import com.kuka.domain.ResultDto;
import com.kuka.domain.Spkfk;
import com.kuka.services.RmkInterfaceService;
import com.kuka.services.SpkfkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SpkfkServiceImpl implements SpkfkService {

    @Autowired
    private RmkInterfaceService rmkInterfaceService;
    @Override
    public void synItems() {
        List<Customer> customers=new ArrayList<>();
        Customer customer = new Customer();
        customer.setArea("test");
        customer.setCity("test");
        customer.setBranchId("'");
        customer.setDanwBh("test");
        customer.setCustName("test");
        customer.setMedicalLicenseNo("L18196198320");
        customer.setStatus(1);
        customers.add(customer);
        ResultDto resultDto = rmkInterfaceService.synCustomers(customers);
        System.out.println(resultDto.getCode()+"---"+resultDto.getMessage());

    }
}
