package com.kuka.dao;

import com.kuka.domain.Spkfk;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpkfkExtMapper {
    //查询1000条料品信息上传
    List<Spkfk> querySpkfk();
    //更新上传成功的标记
    void updateUploadStatus(@Param("spkfks") List<Spkfk> spkfks);
}