package com.kuka.services.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.kuka.config.YamlPropertySourceFactory;
import com.kuka.domain.Customer;
import com.kuka.domain.ResultDto;
import com.kuka.domain.ICustomer;
import com.kuka.exeception.KukaRollbackException;
import com.kuka.services.RmkInterfaceService;
import com.kuka.utils.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@PropertySource(value = {"classpath:application-${spring.profiles.active}.yml"},factory = YamlPropertySourceFactory.class)
public class RmkInterfaceServiceImpl implements RmkInterfaceService {
    @Value("${rmk.clientId}")
    private  String clientId;
    @Value("${rmk.clientSecret}")
    private  String clientSecret;
    @Value("${rmk.branchId}")
    private  String branchId;
    @Value("${rmk.customerUrl}")
    private  String customerUrl;
    @Value("${rmk.productUrl}")
    private  String productUrl;
    @Value("${rmk.clientSecret}")
    private  String orderGetUrl;
    @Value("${rmk.orderMarkUrl}")
    private  String orderMarkUrl;
    @Value("${rmk.orderStatusUrl}")
    private  String orderStatusUrl;
    @Override
    public ResultDto synCustomers(List<Customer> customers) {
        ResultDto resultDto=new ResultDto();
        //构造数据
        ICustomer rmkCustomer=new ICustomer();
        rmkCustomer.setClientId(clientId);
        String timeStamp= DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss");
        String sign = HttpClientUtils.getSign(timeStamp);
        rmkCustomer.setTimestamp(timeStamp);
        rmkCustomer.setSign(sign);
        customers.stream().forEach(t->t.setBranchId(branchId));
        rmkCustomer.setCustomerList(customers);
        try {
            String response = HttpClientUtils.doPost(customerUrl, JSONObject.toJSONString(rmkCustomer));
            JSONObject jsonObject = JSONObject.parseObject(response);
            resultDto.setCode((int)jsonObject.get("code"));
            resultDto.setMessage((String) jsonObject.get("msg"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new KukaRollbackException("同步客户资料错误:"+e.getMessage());
        }
     return resultDto;
    }

    @Override
    public ResultDto synItems(List<String> items) {
        ResultDto resultDto=new ResultDto();
        return resultDto;
    }

    @Override
    public List<String> synOrder() {
        return null;
    }

    @Override
    public ResultDto synOrderMark(List<String> orderNo) {
        ResultDto resultDto=new ResultDto();
        return resultDto;
    }

    @Override
    public ResultDto synOrderStatus(List<String> orderNo) {
        ResultDto resultDto=new ResultDto();
        return resultDto;
    }
}
