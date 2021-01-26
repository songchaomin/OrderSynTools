package com.kuka.dao;

import com.kuka.domain.SalOrder;
import com.kuka.domain.SalOrderLine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SalOrderExtMapper {
    List<SalOrder> queryOrderByList(@Param("orderNos") List<String> orderNos);

    List<SalOrder> queryOrderStatus();

    List<SalOrderLine> queryOrderLineByStatus(@Param("orderNos") List<String> outOrderCodes);
}