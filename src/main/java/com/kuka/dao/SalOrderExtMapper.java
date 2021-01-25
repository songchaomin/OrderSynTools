package com.kuka.dao;

import com.kuka.domain.SalOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SalOrderExtMapper {
    List<SalOrder> queryOrderByList(@Param("orderNos") List<String> orderNos);
}