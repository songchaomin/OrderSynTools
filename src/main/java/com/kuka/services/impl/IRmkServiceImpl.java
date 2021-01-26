package com.kuka.services.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kuka.config.YamlPropertySourceFactory;
import com.kuka.domain.*;
import com.kuka.services.IRmkService;
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
public class IRmkServiceImpl implements IRmkService {
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

    @Value("${rmk.orderGetUrl}")
    private  String orderGetUrl;

    @Value("${rmk.orderMarkUrl}")
    private  String orderMarkUrl;

    @Value("${rmk.orderStatusUrl}")
    private  String orderStatusUrl;

    @Value("${rmk.invAndPriceUrl}")
    private  String invAndPriceUrl;
    @Override
    public ResultDto synCustomers(List<Customer> customers) {
        ResultDto resultDto=new ResultDto();
        //构造数据
        ICustomer rmkCustomer=new ICustomer();
        rmkCustomer.setClientId(clientId);
        String timeStamp= DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss");
        String sign = HttpClientUtils.getSign(clientId,clientSecret,timeStamp);
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
            resultDto.setCode(999);
            resultDto.setMessage(e.getMessage());
        }
     return resultDto;
    }

    @Override
    public ResultDto synProducts(List<Product> products) {
        ResultDto resultDto=new ResultDto();
        //构造数据
        IProduct rmkProduct=new IProduct();
        rmkProduct.setClientId(clientId);
        String timeStamp= DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss");
        String sign = HttpClientUtils.getSign(clientId,clientSecret,timeStamp);
        rmkProduct.setTimestamp(timeStamp);
        rmkProduct.setSign(sign);
        products.stream().forEach(t->t.setBranchId(branchId));
        rmkProduct.setMerchandiseList(products);
        try {
            String response = HttpClientUtils.doPost(productUrl, JSONObject.toJSONString(rmkProduct));
            JSONObject jsonObject = JSONObject.parseObject(response);
            resultDto.setCode((int)jsonObject.get("code"));
            resultDto.setMessage((String) jsonObject.get("msg"));
        } catch (Exception e) {
            e.printStackTrace();
            resultDto.setCode(999);
            resultDto.setMessage(e.getMessage());
        }
        return resultDto;
    }

    @Override
    public IOrder synOrder() {
        JSONObject jsonObject=new JSONObject();
        IOrder iOrder=new IOrder();
        String timeStamp= DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss");
        String sign = HttpClientUtils.getSign(clientId,clientSecret,timeStamp);
        jsonObject.put("sign",sign);
        jsonObject.put("timestamp",timeStamp);
        jsonObject.put("clientId",clientId);
        jsonObject.put("branchId",branchId);
        try {
            String response = HttpClientUtils.doPost(orderGetUrl,jsonObject.toJSONString() );
            JSONObject jsonResponse = JSONObject.parseObject(response);
            iOrder.setCode((int)jsonResponse.get("code"));
            iOrder.setMsg((String) jsonResponse.get("msg"));
            JSONArray orderList = jsonResponse.getJSONArray("orderList");
            if (orderList.size()>0){
                iOrder.setOrderList(orderList.toJavaList(SalOrder.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return iOrder;
    }

    @Override
    public ResultDto synOrderStatus(SalOrder salOrder) {
        ResultDto resultDto=new ResultDto();
        JSONObject jsonObject=new JSONObject();
        String timeStamp= DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss");
        String sign = HttpClientUtils.getSign(clientId,clientSecret,timeStamp);
        jsonObject.put("sign",sign);
        jsonObject.put("timestamp",timeStamp);
        jsonObject.put("clientId",clientId);
        jsonObject.put("branchId",branchId);
        jsonObject.put("outOrderCode",salOrder.getOutOrderCode());
        jsonObject.put("orderCode",salOrder.getOrderCode());
        jsonObject.put("danwBh",salOrder.getDanwBh());
        jsonObject.put("orderStatus",5);
        jsonObject.put("payAmount",salOrder.getPayAmount());
        jsonObject.put("payStatus",1);
        jsonObject.put("failMsg","订单回传状态失败");
        jsonObject.put("orderDetail",salOrder.getOrderDetail());
        try {
            String response = HttpClientUtils.doPost(orderStatusUrl,jsonObject.toJSONString() );
            JSONObject jsonResponse = JSONObject.parseObject(response);
            resultDto.setCode((int)jsonResponse.get("code"));
            resultDto.setMessage((String) jsonResponse.get("msg"));
        } catch (Exception e) {
            e.printStackTrace();
            resultDto.setCode(999);
            resultDto.setMessage(e.getMessage());
        }
        return resultDto;
    }

    @Override
    public ResultDto markerOrderStatus(List<String> orderNos) {
        ResultDto resultDto=new ResultDto();
        JSONObject jsonObject=new JSONObject();
        String timeStamp= DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss");
        String sign = HttpClientUtils.getSign(clientId,clientSecret,timeStamp);
        jsonObject.put("sign",sign);
        jsonObject.put("timestamp",timeStamp);
        jsonObject.put("clientId",clientId);
        jsonObject.put("branchId",branchId);
        jsonObject.put("orderCodeList",orderNos);
        try {
            String response = HttpClientUtils.doPost(orderMarkUrl,jsonObject.toJSONString() );
            JSONObject jsonResponse = JSONObject.parseObject(response);
            resultDto.setCode((int)jsonResponse.get("code"));
            resultDto.setMessage((String) jsonResponse.get("msg"));
        } catch (Exception e) {
            e.printStackTrace();
            resultDto.setCode(999);
            resultDto.setMessage(e.getMessage());
        }
        return resultDto;
    }

    @Override
    public ResultDto synInventoryAndPrice(List<InventoryAndPrice> inventoryAndPrices) {
        ResultDto resultDto=new ResultDto();
        //构造数据
        IInventoryAndPrice inventoryAndPrice=new IInventoryAndPrice();
        inventoryAndPrice.setClientId(clientId);
        String timeStamp= DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss");
        String sign = HttpClientUtils.getSign(clientId,clientSecret,timeStamp);
        inventoryAndPrice.setTimestamp(timeStamp);
        inventoryAndPrice.setSign(sign);
        inventoryAndPrice.setBranchId(branchId);
        inventoryAndPrice.setProdNoList(inventoryAndPrices);
        try {
            String response = HttpClientUtils.doPost(invAndPriceUrl, JSONObject.toJSONString(inventoryAndPrice));
            JSONObject jsonObject = JSONObject.parseObject(response);
            resultDto.setCode((int)jsonObject.get("code"));
            resultDto.setMessage((String) jsonObject.get("msg"));
        } catch (Exception e) {
            e.printStackTrace();
            resultDto.setCode(999);
            resultDto.setMessage(e.getMessage());
        }
        return resultDto;
    }
}
