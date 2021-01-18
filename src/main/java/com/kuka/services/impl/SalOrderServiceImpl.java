package com.kuka.services.impl;

import com.kuka.dao.SalOrderLineMapper;
import com.kuka.dao.SalOrderMapper;
import com.kuka.domain.IOrder;
import com.kuka.domain.SalOrder;
import com.kuka.domain.SalOrderLine;
import com.kuka.exeception.KukaRollbackException;
import com.kuka.services.IRmkService;
import com.kuka.services.SalOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void synOrder() {
        IOrder iOrder = iRmkService.synOrder();
        //校验数据
        validateOrder(iOrder);
        //同步数据
        handlerOrder(iOrder);
        //回调接口（更新订单同步标记）
        synOrderStatus(iOrder);
    }

    private void synOrderStatus(IOrder iOrder) {
        List<String> orderNos=new ArrayList<>();
        iOrder.getOrderList().stream().forEach(t->{
            orderNos.add(t.getOutOrderCode());
        });
        iRmkService.synOrderStatus(orderNos);
    }

    private void handlerOrder(IOrder iOrder) {
        List<SalOrder> orderList = iOrder.getOrderList();
        List<SalOrderLine> orderLines=new ArrayList<>();
        orderList.stream().forEach(t->{
            List<SalOrderLine> orderDetail = t.getOrderDetail();
            orderDetail.stream().forEach(s->{
                SalOrderLine salOrderLine=new SalOrderLine();
                salOrderLine.setOutOrderCode(t.getOutOrderCode());
                salOrderLine.setPrice(s.getPrice());
                salOrderLine.setProdNo(s.getProdNo());
                salOrderLine.setQuantity(s.getQuantity());
                orderLines.add(salOrderLine);
            });
        });
        try {
            salOrderMapper.batchInsert(orderList);
            salOrderLineMapper.batchInsert(orderLines);
        } catch (Exception e) {
            throw new KukaRollbackException(e.getMessage());
        }
    }

    private void validateOrder(IOrder iOrder) {
        if (iOrder.getCode()==0){
            log.error("接口同步有问题！原因："+iOrder.getMsg());
            throw new KukaRollbackException("接口同步有问题！原因："+iOrder.getMsg());
        }

        if (CollectionUtils.isEmpty(iOrder.getOrderList())){
            log.info("本次无可同步的订单数据！");
            throw new KukaRollbackException("本次无可同步的订单数据!");
        }
    }
}
