package com.kuka.services.impl;

import com.kuka.dao.SalOrderExtMapper;
import com.kuka.dao.SalOrderLineMapper;
import com.kuka.dao.SalOrderMapper;
import com.kuka.dao.SpkfkExtMapper;
import com.kuka.domain.*;
import com.kuka.enums.OperatorTypeEnum;
import com.kuka.exeception.KukaRollbackException;
import com.kuka.services.IRmkService;
import com.kuka.services.SalOrderService;
import com.kuka.utils.LogUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class SalOrderServiceImpl implements SalOrderService {
    @Autowired
    private IRmkService iRmkService;
    @Autowired
    private SalOrderMapper salOrderMapper;
    @Autowired
    private SalOrderLineMapper salOrderLineMapper;
    @Autowired
    private LogUtils logUtils;
    @Autowired
    private SalOrderExtMapper salOrderExtMapper;
    @Autowired
    private SpkfkExtMapper spkfkExtMapper;
    @Override
    public ResultDto synOrder() {
        ResultDto resultDto=new ResultDto();
        IOrder iOrder = iRmkService.synOrder();
        //校验数据
        if (iOrder.getCode()!=0){
            logUtils.makeLog("0","订单同步有问题！原因："+iOrder.getMsg(), OperatorTypeEnum.ORDER.getType());
            log.error("订单同步有问题！原因："+iOrder.getMsg());
            resultDto.setCode(0);
            resultDto.setMessage("订单同步有问题！原因："+iOrder.getMsg());
        }

        if (CollectionUtils.isEmpty(iOrder.getOrderList())){
            log.info("已同步完成，此次同步的数据为0条,请查询。");
            logUtils.makeLog("1","已同步完成，此次同步的数据为0条,请查询。", OperatorTypeEnum.ORDER.getType());
            resultDto.setCode(1);
            resultDto.setMessage("已同步完成，此次同步的数据为0条,请查询。");
            return resultDto ;
        }
        //同步数据
        handlerOrder(iOrder,resultDto);
        //回调接口（更新订单同步标记）
        makerOrderStatus(iOrder,resultDto);
        return resultDto;
    }

    private void makerOrderStatus(IOrder iOrder, ResultDto resultDto) {
        List<String> orderNos=new ArrayList<>();
        iOrder.getOrderList().stream().forEach(t->{
            orderNos.add(t.getOutOrderCode());
        });
        iRmkService.markerOrderStatus(orderNos);
    }

    private void handlerOrder(IOrder iOrder,ResultDto resultDto) {
        List<SalOrder> orderList = iOrder.getOrderList();
        //获取所有的订单号
        List<String> orderNos = orderList.stream().map(t -> t.getOutOrderCode()).collect(Collectors.toList());
        //查询中间表所有的订单号
        List<SalOrder> salOrders = salOrderMapper.queryOrderByList(orderNos);
        Map<String,SalOrder> salordersMap=salOrders.stream().collect(Collectors.toMap(SalOrder::getOutOrderCode, t->t,(v1, v2)->v2));
        List<SalOrderLine> insertOrderLines=new ArrayList<>();
        List<SalOrder> insertSalOrder=new ArrayList<>();
        orderList.stream().forEach(t->{
            if (!salordersMap.containsKey(t.getOutOrderCode())){
                t.setUploadStatus((byte)0);
                insertSalOrder.add(t);
                List<SalOrderLine> orderDetail = t.getOrderDetail();
                orderDetail.stream().forEach(s->{
                    SalOrderLine salOrderLine=new SalOrderLine();
                    salOrderLine.setOutOrderCode(t.getOutOrderCode());
                    salOrderLine.setPrice(s.getPrice());
                    salOrderLine.setProdNo(s.getProdNo());
                    salOrderLine.setQuantity(s.getQuantity());
                    insertOrderLines.add(salOrderLine);
                });
            }
        });
        try {
            if (!CollectionUtils.isEmpty(insertSalOrder)){
                salOrderMapper.batchInsert(insertSalOrder);
            }
            if (!CollectionUtils.isEmpty(insertOrderLines)){
                salOrderLineMapper.batchInsert(insertOrderLines);
            }
            resultDto.setCode(1);
            resultDto.setMessage("订单同步完成，此次同步的数据为"+insertSalOrder.size()+"条,请查询。");
            logUtils.makeLog("1","订单同步完成，此次同步的数据为"+insertSalOrder.size()+"条,请查询。", OperatorTypeEnum.ORDER.getType());
        } catch (Exception e) {
            logUtils.makeLog("0","订单同步有问题！原因："+e.getMessage(), OperatorTypeEnum.ORDER.getType());
            resultDto.setCode(0);
            resultDto.setMessage("订单同步有问题！原因："+e.getMessage());
            throw new KukaRollbackException(e.getMessage());
        }
    }


    @Override
    public ResultDto synOrderStatus() {
        ResultDto resultDto=new ResultDto();
        //查询变动的订单状态集合
        List<SalOrder> salOrders = salOrderExtMapper.queryOrderStatus();
        if (!CollectionUtils.isEmpty(salOrders)){
            List<String> outOrderCodes = salOrders.stream().map(t -> t.getOutOrderCode()).collect(Collectors.toList());
            List<SalOrderLine> salorderDetail = salOrderExtMapper.queryOrderLineByStatus(outOrderCodes);
            Map<String, List<SalOrderLine>>orderDetailMap = salorderDetail.stream().collect(Collectors.groupingBy(SalOrderLine::getOutOrderCode));
            for (SalOrder salOrder:salOrders){
                String outOrderCode = salOrder.getOutOrderCode();
                if (orderDetailMap.containsKey(outOrderCode)){
                    salOrder.setOrderDetail(orderDetailMap.get(outOrderCode));
                }
                 resultDto = iRmkService.synOrderStatus(salOrder,0);
                if (resultDto.getCode()==0){
                    //更新同步状态
                    SalOrder updateSalOrder=new SalOrder();
                    updateSalOrder.setOutOrderCode(outOrderCode);
                    updateSalOrder.setUploadStatus((byte)1);
                    salOrderMapper.updateByPrimaryKeySelective(updateSalOrder);
                    logUtils.makeLog("1","订单号："+outOrderCode+"同步上传成功！",OperatorTypeEnum.ORDERSTATUS.getType());
                }
            }
            //上传库存数据
            List<String> spids = salorderDetail.stream().map(t -> t.getProdNo()).collect(Collectors.toList());
            List<InventoryAndPrice> inventoryAndPrices = spkfkExtMapper.querySpkfkJcBySpid(spids);
            ResultDto invResultDto = iRmkService.synInventoryAndPrice(inventoryAndPrices);
            if (invResultDto.getCode()==0){
                logUtils.makeLog("1","订单状态更新后同步库存数据成功！",OperatorTypeEnum.ORDERSTATUS.getType());
            }else{
                logUtils.makeLog("0","订单状态更新后同步库存数据不成功！原因："+invResultDto.getMessage(),OperatorTypeEnum.ORDERSTATUS.getType());
            }
        }else{
            resultDto.setCode(1);
            resultDto.setMessage("本次订单状态同步已完成，共上传了【0】条订单");
        }
        return resultDto;
    }

    @Override
    public ResultDto synRebackOrderStatus() {
        ResultDto resultDto=new ResultDto();
        //查询变动的订单状态集合
        List<SalOrder> salOrders = salOrderExtMapper.queryBackOrderStatus();
        if (!CollectionUtils.isEmpty(salOrders)){
            List<String> outOrderCodes = salOrders.stream().map(t -> t.getOutOrderCode()).collect(Collectors.toList());
            List<SalOrderLine> salorderDetail = salOrderExtMapper.queryBackOrderLineByStatus(outOrderCodes);
            Map<String, List<SalOrderLine>>orderDetailMap = salorderDetail.stream().collect(Collectors.groupingBy(SalOrderLine::getOutOrderCode));
            for (SalOrder salOrder:salOrders){
                String outOrderCode = salOrder.getOutOrderCode();
                if (orderDetailMap.containsKey(outOrderCode)){
                    salOrder.setOrderDetail(orderDetailMap.get(outOrderCode));
                }
                resultDto = iRmkService.synOrderStatus(salOrder,1);
                if (resultDto.getCode()==0){
                    //更新同步状态
                    SalOrder updateSalOrder=new SalOrder();
                    updateSalOrder.setOutOrderCode(outOrderCode);
                    updateSalOrder.setUploadStatus((byte)1);
                    salOrderMapper.updateByPrimaryKeySelective(updateSalOrder);
                    logUtils.makeLog("1","订单号："+outOrderCode+"同步上传成功！",OperatorTypeEnum.ORDERSTATUS.getType());
                }
            }
            //上传库存数据
            List<String> spids = salorderDetail.stream().map(t -> t.getProdNo()).collect(Collectors.toList());
            List<InventoryAndPrice> inventoryAndPrices = spkfkExtMapper.querySpkfkJcBySpid(spids);
            ResultDto invResultDto = iRmkService.synInventoryAndPrice(inventoryAndPrices);
            if (invResultDto.getCode()==0){
                logUtils.makeLog("1","订单状态更新后同步库存数据成功！",OperatorTypeEnum.ORDERSTATUS.getType());
            }else{
                logUtils.makeLog("0","订单状态更新后同步库存数据不成功！原因："+invResultDto.getMessage(),OperatorTypeEnum.ORDERSTATUS.getType());
            }
        }else{
            resultDto.setCode(1);
            resultDto.setMessage("本次订单状态同步已完成，共上传了【0】条订单");
        }
        return resultDto;
    }
}
