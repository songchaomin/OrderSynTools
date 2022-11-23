package com.kuka.dao;

import com.kuka.domain.SalOrder;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SalOrderMapper {
    int deleteByPrimaryKey(String outOrderCode);

    int insert(SalOrder record);

    int insertSelective(SalOrder record);

    SalOrder selectByPrimaryKey(String outOrderCode);

    int updateByPrimaryKeySelective(SalOrder record);

    int updateByPrimaryKey(SalOrder record);

    int batchInsert(@Param("list") List<SalOrder> list);

    List<SalOrder> queryOrderByList(@Param("orderNos") List<String> orderNos);
}
