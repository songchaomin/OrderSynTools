package com.kuka.services.impl;

import com.alibaba.fastjson.JSONObject;
import com.kuka.dao.SpkfkExtMapper;
import com.kuka.domain.InventoryAndPrice;
import com.kuka.domain.Product;
import com.kuka.domain.ResultDto;
import com.kuka.domain.Spkfk;
import com.kuka.services.IRmkService;
import com.kuka.services.SpkfkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SpkfkServiceImpl implements SpkfkService {

    @Autowired
    private IRmkService rmkInterfaceService;
    @Autowired
    private SpkfkExtMapper spkfkExtMapper;
    @Override
    public void synItems() {
        //查询要上传的商品资料
        List<Spkfk> spkfks = spkfkExtMapper.querySpkfk();
        if (CollectionUtils.isEmpty(spkfks)){
            log.info("本次没有可上传的商品资料。");
            return ;
        }
        List<Product> products = new ArrayList<>();
        spkfks.stream().forEach(t -> {
            Product product = new Product();
            product.setProdNo(t.getSpbh());
            product.setProdName(t.getSpmch());
            product.setProdBarcode(t.getSptm());//条形码
            product.setSpecification(t.getShpgg());
            product.setPackageUnit(t.getDw());
            product.setSplit_package_type(1);
            product.setMidPackageQuantity(t.getJlgg());
            product.setStatus(1);
            products.add(product);
        });
        ResultDto resultDto = rmkInterfaceService.synProducts(products);
        if (resultDto.getCode() != 0) {
            log.error("上传客户信息失败，原因：" + resultDto.getMessage());
        } else {
            //更新上传成功标记
            spkfkExtMapper.updateUploadStatus(spkfks);
            log.info("上传成功，此次上传的客户编码信息为：" + JSONObject.toJSONString(spkfks.stream().map(t -> t.getSpbh().trim()).collect(Collectors.toList())));
        }
    }

    @Override
    public void synInventoryAndPrice() {
        List<InventoryAndPrice> inventoryAndPrices = spkfkExtMapper.querySpkfkJc();
        ResultDto resultDto = rmkInterfaceService.synInventoryAndPrice(inventoryAndPrices);
        if (resultDto.getCode() != 0) {
            log.error("上传库存和商品价格失败，原因：" + resultDto.getMessage());
        } else {
            //更新上传成功标记
            log.info("上传成功，此次上传的库存和商品价格信息为：" + JSONObject.toJSONString(inventoryAndPrices.stream().map(t -> t.getProdNo().trim()).collect(Collectors.toList())));
        }
    }
}
