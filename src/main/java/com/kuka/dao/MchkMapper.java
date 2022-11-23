package com.kuka.dao;

import com.kuka.domain.Mchk;
import org.springframework.stereotype.Repository;

@Repository
public interface MchkMapper {
    int deleteByPrimaryKey(String dwbh);

    int insert(Mchk record);

    int insertSelective(Mchk record);

    Mchk selectByPrimaryKey(String dwbh);

    int updateByPrimaryKeySelective(Mchk record);

    int updateByPrimaryKey(Mchk record);
}
