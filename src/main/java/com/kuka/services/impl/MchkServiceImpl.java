package com.kuka.services.impl;

import com.alibaba.fastjson.JSONObject;
import com.kuka.dao.MchkExtMapper;
import com.kuka.domain.Customer;
import com.kuka.domain.Mchk;
import com.kuka.domain.ResultDto;
import com.kuka.services.IRmkService;
import com.kuka.services.MchkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MchkServiceImpl implements MchkService {
    @Autowired
    private MchkExtMapper mchkExtMapper;
    @Autowired
    private IRmkService iRmkService;
    @Override
    public void synCustomer() {
        //查询100条客户信息上传到润美康
        List<Mchk> mchks = mchkExtMapper.queryMchk();
        if (CollectionUtils.isEmpty(mchks)){
            log.info("本次没有可上传的客户信息。");
            return ;
        }
        List<Customer> customers=new ArrayList<>();
        mchks.stream().forEach(t->{
            Customer customer=new Customer();
            customer.setDanwBh(t.getDanwbh());
            customer.setCustName(t.getDwmch());
            customer.setMedicalLicenseNo(t.getXvkz());
            customer.setStatus(1);
            customers.add(customer);
        });
        ResultDto resultDto = iRmkService.synCustomers(customers);
        if (resultDto.getCode()!=0){
            log.error("上传客户信息失败，原因："+resultDto.getMessage());
        }else{
            //更新上传成功标记
            mchkExtMapper.updateUploadStatus(mchks);
            log.info("上传成功，此次上传的客户编码信息为："+ JSONObject.toJSONString(mchks.stream().map(t->t.getDanwbh().trim()).collect(Collectors.toList())));
        }
    }
}
