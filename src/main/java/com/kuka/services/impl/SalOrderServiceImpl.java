package com.kuka.services.impl;

import com.kuka.dao.SalOrderLineMapper;
import com.kuka.dao.SalOrderMapper;
import com.kuka.domain.IOrder;
import com.kuka.domain.ResultDto;
import com.kuka.domain.SalOrder;
import com.kuka.domain.SalOrderLine;
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
        synOrderStatus(iOrder,resultDto);
        return resultDto;
    }

    private void synOrderStatus(IOrder iOrder,ResultDto resultDto) {
        List<String> orderNos=new ArrayList<>();
        iOrder.getOrderList().stream().forEach(t->{
            orderNos.add(t.getOutOrderCode());
        });
        iRmkService.synOrderStatus(orderNos);
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

}
