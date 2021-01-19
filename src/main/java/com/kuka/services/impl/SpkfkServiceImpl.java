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
    public ResultDto synItems() {
        ResultDto resultDto=new ResultDto();
        //查询要上传的商品资料
        List<Spkfk> spkfks = spkfkExtMapper.querySpkfk();
        if (CollectionUtils.isEmpty(spkfks)){
            log.info("本次没有可上传的商品资料。");
            resultDto.setCode(1);
            resultDto.setMessage("商品资料已经全部同步完毕,无需再同步！");
            return resultDto ;
        }
        List<Product> products = new ArrayList<>();
        spkfks.stream().forEach(t -> {
            Product product = new Product();
            product.setProdNo(t.getSpbh().trim());
            product.setProdName(t.getSpmch());
            product.setProdBarcode(t.getSptm());//条形码
            product.setSpecification(t.getShpgg());
            product.setPackageUnit(t.getDw());
            product.setSplit_package_type(1);
            product.setMidPackageQuantity(t.getJlgg());
            product.setStatus(1);
            products.add(product);
        });
         resultDto = rmkInterfaceService.synProducts(products);
        if (resultDto.getCode() != 0) {
            log.error("上传商品信息失败，原因：" + resultDto.getMessage());
            resultDto.setCode(0);
            resultDto.setMessage("上传商品信息失败，原因：" + resultDto.getMessage());
        } else {
            //更新上传成功标记
            spkfkExtMapper.updateUploadStatus(spkfks);
            log.info("上传成功，此次上传的商品信息编码为：" + JSONObject.toJSONString(spkfks.stream().map(t -> t.getSpbh().trim()).collect(Collectors.toList())));
            resultDto.setCode(1);
            resultDto.setMessage("商品资料上传成功！");
        }
        return resultDto;
    }

    @Override
    public ResultDto synInventoryAndPrice() {
        ResultDto resultDto=new ResultDto();
        List<InventoryAndPrice> inventoryAndPrices = spkfkExtMapper.querySpkfkJc();
        inventoryAndPrices.stream().forEach(t->t.setProdNo(t.getProdNo().trim()));
         resultDto = rmkInterfaceService.synInventoryAndPrice(inventoryAndPrices);
        if (resultDto.getCode() != 0) {
            log.error("上传库存和商品价格失败，原因：" + resultDto.getMessage());
            resultDto.setCode(0);
            resultDto.setMessage("上传库存和商品价格失败，原因：" + resultDto.getMessage());
        } else {
            //更新上传成功标记
            log.info("上传成功，此次上传的库存和商品价格信息为：" + JSONObject.toJSONString(inventoryAndPrices.stream().map(t -> t.getProdNo().trim()).collect(Collectors.toList())));
            resultDto.setCode(1);
            resultDto.setMessage("库存和商品价格资料上传成功！");
        }
        return resultDto;
    }
}
