package com.kuka.services.impl;

import com.kuka.dao.OperatorLogMapper;
import com.kuka.domain.OperatorLog;
import com.kuka.domain.ResultPageDto;
import com.kuka.services.OperatorLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OperatorLogServiceImpl implements OperatorLogService {
    @Autowired
    private OperatorLogMapper operatorLogMapper;
    @Override
    public ResultPageDto<List<OperatorLog>> queryOperatorLog(ResultPageDto pageDto) {
        ResultPageDto<List<OperatorLog>> resultPageDto=new ResultPageDto<>();
        //查询总记录数
        long count = operatorLogMapper.queryOperatorLogCount();
        if (count>0){
            //查询当前记录数
            List<OperatorLog> data = operatorLogMapper.queryOperatorLogList(pageDto);
            resultPageDto.setCode(0);
            resultPageDto.setMsg("success");
            resultPageDto.setData(data);
            resultPageDto.setCount(count);
            resultPageDto.setPage(pageDto.getPage());
            resultPageDto.setLimit(pageDto.getLimit());
        }
        return resultPageDto;
    }
}
