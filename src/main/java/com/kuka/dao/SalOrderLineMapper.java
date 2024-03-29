package com.kuka.dao;

import com.kuka.domain.SalOrderLine;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SalOrderLineMapper {
    int insert(SalOrderLine record);

    int insertSelective(SalOrderLine record);

    int batchInsert(@Param("list") List<SalOrderLine> list);
}
