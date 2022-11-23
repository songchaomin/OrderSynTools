package com.kuka.dao;

import com.kuka.domain.SalOrder;
import com.kuka.domain.SalOrderLine;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SalOrderExtMapper {
    List<SalOrder> queryOrderByList(@Param("orderNos") List<String> orderNos);

    List<SalOrder> queryOrderStatus();

    List<SalOrderLine> queryOrderLineByStatus(@Param("orderNos") List<String> outOrderCodes);

    List<SalOrder> queryBackOrderStatus();

    List<SalOrderLine> queryBackOrderLineByStatus(List<String> outOrderCodes);
}
