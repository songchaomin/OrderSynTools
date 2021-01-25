package com.kuka.dao;

import com.kuka.domain.OperatorLog;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OperatorLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OperatorLog record);

    int insertSelective(OperatorLog record);

    OperatorLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OperatorLog record);

    int updateByPrimaryKey(OperatorLog record);

    int batchInsert(@Param("list") List<OperatorLog> list);
}