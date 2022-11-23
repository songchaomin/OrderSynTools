package com.kuka.dao;

import com.kuka.domain.Mchk;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MchkExtMapper {
    //查询100条客户信息上传
    List<Mchk> queryMchk();
    //更新上传成功的标记
    void updateUploadStatus(@Param("mchks") List<Mchk> mchks);
}
